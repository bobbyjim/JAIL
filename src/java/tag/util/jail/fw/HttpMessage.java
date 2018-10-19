package tag.util.jail.fw;

import java.io.*;
import java.net.*;
import java.util.*;

public class HttpMessage
{
       URL    url;
       String args;
       String jsessionid = null;

   public HttpMessage(URL url)
   {
      this.url  = null;
      this.args = null;
      this.url  = url;
   }

   public URLConnection sendGetMessage()
      throws IOException
   {
      return sendGetMessage(null);
   }

   public URLConnection sendGetMessage( Hashtable args )
      throws IOException
   {
      return sendGetMessage( args, null );
   }

   public URLConnection sendGetMessage( Hashtable args, Hashtable cookies )
      throws IOException
   {
      String argString    = toEncodedString( args,    "&" );
      //String cookieString = toEncodedString( cookies, ";" );
      String cookieString = jsessionid;

      //
      //   Careful... perhaps the argString should be non-empty
      //   before we prepend the question mark?
      //
      //String surl = url.toExternalForm() + "?" + argString;
      //System.err.println( "url: " + surl );
      
      URL myurl = new URL( url.toExternalForm() + "?" + argString );

      URLConnection con = myurl.openConnection();
      con.setUseCaches(false);
      
      if ( cookieString != null )
         con.setRequestProperty("Cookie", cookieString);

/*
      BufferedReader br = new BufferedReader
      (
         new InputStreamReader( con.getInputStream() )
      );
      for(;;)
      {
         String in = br.readLine();
         if ( in == null ) break;
         System.err.println( in );
      }
*/
      return con;
   }



   public URLConnection sendPostMessage()
      throws IOException
   {
      return sendPostMessage(null);
   }

   public URLConnection sendPostMessage( Hashtable args )
      throws IOException
   {
      return sendPostMessage(args, null);
   }

   public URLConnection sendPostMessage( Hashtable args, Hashtable cookies)
       throws IOException
   {
      String argString    = toEncodedString( args,    "&" );
      String cookieString = jsessionid; // toEncodedString( cookies, ";" );

      URL myurl = new URL( url.toExternalForm() + "?" + argString );

      System.err.println( "url: " + myurl.toExternalForm() );

      URLConnection con = myurl.openConnection();
      con.setDoInput(true);
//      con.setDoOutput(true);
      con.setUseCaches(false);
      con.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded" );

      if ( cookieString != null )
         con.setRequestProperty( "Cookie", cookieString );

/*         
      DataOutputStream out = new DataOutputStream( con.getOutputStream() );
      out.writeBytes(argString);
      out.flush();
      out.close();
*/
      //
      //   Print out the HTTP header info.
      //
/*
      for( int i=0; i<10; i++ )
      {
         System.err.println( con.getHeaderFieldKey( i ) 
                           + ": " 
                           + con.getHeaderField( i ) );
      }
      System.err.println( "\n" );
*/
      //
      //   Print out the response HTML.
      // 
/*
      InputStream is = con.getInputStream();
      
      byte[] buffer = new byte[ is.available() ];
      is.read( buffer );
      is.close();
   
      String buf = new String( buffer );
      System.err.println( buf );
*/
/*      
      BufferedReader br = new BufferedReader
      (
         new InputStreamReader( con.getInputStream() )
      );
      for(;;)
      {
         String in = br.readLine();
         if ( in == null ) break;
         System.err.println( in );
      }
*/
      return con;
   }
   
   private String toEncodedString( Hashtable args, String delim )
   {
      if ( args == null )
         return "";
      else
      {
         StringBuffer buf   = new StringBuffer();
         Enumeration  names = args.keys();
         
         do
         {
            if( names.hasMoreElements() == false ) break;
         
            String name  = names.nextElement().toString();
            String value = args.get(name).toString();
            
            buf.append( name + "=" + value );
            
            //buf.append( HttpMessage.encode(name) 
            //          + "=" 
            //          + HttpMessage.encode(value) );
            
            if( names.hasMoreElements() ) buf.append( delim );
         }
         while(true);
         
         return buf.toString();
      }
   }
    
   public static String encode( String s )
   {
      StringBuffer buff = new StringBuffer();
 
      for (int i = 0; i < s.length(); i++)
      {
         char c = s.charAt(i);
 
         if (c == ' ')
            buff.append('+');
         else if (
            (c >= 'A' && c <= 'Z') ||
            (c >= 'a' && c <= 'z') ||
            (c >= '0' && c <= '9') ||
            (c == '.') || (c == '-') ||  //WQ: Added in 1.3
            (c == '_') || (c == '*') ||  //WQ: Added in 1.3
            (c == '=') || (c == '&')     //WQ: needed to make my life easier
                 )
            buff.append(c);
         else
         {
            buff.append('%');
            buff.append(Character.forDigit((c >> 4) & 0x0F, 16));
            buff.append(Character.forDigit(c & 0x0F, 16));
         }
      }
      return buff.toString();
   }    
      
   public void getJSessionID( URLConnection con )
   {
   	jsessionid = con.getHeaderField( "Set-cookie");
   	//System.err.println( "jsessionid = " + jsessionid );
   }
   
   public void setJSessionID( String jsessionid )
   {
      this.jsessionid = jsessionid;
   }
   
   public static void main( String[] args ) throws Exception
   {
      
      URL url = new URL( "HTTP", "47.104.12.12", "/prov/direct/loginServlet" );
      HttpMessage msg = new HttpMessage( url );
      
      //msg.sendGetMessage();
      
      Hashtable attribs = new Hashtable();
      attribs.put( "ims_domain", "default" );
      attribs.put( "login",     "admin" );
      attribs.put( "passwd",    "admin" );
      
      URLConnection con = msg.sendPostMessage( attribs );
      msg.getJSessionID( con );
      
      int i=0;
      do
      {
         System.err.println( con.getHeaderField( i++ ) );
      }
      while( con.getHeaderFieldKey( i ) != null );
      

      InputStream is = con.getInputStream();
      
      byte[] buffer = new byte[ is.available() ];
      is.read( buffer );
      is.close();
   
      String buf = new String( buffer );
      System.err.println( buf );
      
      /*
      URL addDude = new URL
      ( 
         "HTTP", 
         "47.104.12.12",
         "/prov/control/userServlet?action=1&domainId=18&user_name=rje"
      );
      
      attribs = new Hashtable();
      attribs.put( "action",    "1"  );
      attribs.put( "domainId",  "36" );
      attribs.put( "user_name", "rje" );
      attribs.put( "password",  "rje" );
      msg.doit( attribs );
      */
   }
}
