package tag.util.jail.builtin;

import java.io.*;
import java.net.*;
import java.util.*;
import tag.util.*;
import tag.util.jail.fw.*;
import tag.util.jail.*;

/**
*
*   JF_http: sends an HTTP message and returns the response.<br><br>
*
*   Example:<br><br>
*
*   (http.send method host port path (property-pairs) data)<br>
*
*/
public class JF_hurlSend extends JailFunction implements Culpable, Cloneable
{
   protected String[][] getSyntax()  { return JailFunction.NULLSYNTAX; }
   protected boolean evaluateArguments() { return true; }
   
   protected Culpable applyCommand()    
   {
      String     method   = getStringArg(1);
      String     host     = getStringArg(2);
      int        port     = getIntArg(3);
      String     path     = getStringArg(4);
      JailVector props    = new JailVector();
      
      if ( size() > 5 )
         props = getVectorArg( 5 );
      
      JailVector response = new JailVector();
      
      System.err.println( "method(" + method + ")\n"
                        + "host(" + host + ")\n"
                        + "port(" + port + ")\n"
                        + "path(" + path + ")\n" );
      try
      {
         URL url = new URL( "HTTP", 
                            host,
                            port,
                            path );
                         
         HttpURLConnection huc = (HttpURLConnection) url.openConnection();
      
         huc.setRequestMethod( method.toString() );
            
         for( int i=0; i<props.size(); i++ )
         {
            JailVector property = (JailVector) props.elementAt( i );
            String     key      = property.elementAt(0).toString();
            String     val      = property.elementAt(1).toString();
            System.err.println( "Request Property(" + key + "," + val + ")" );
            huc.setRequestProperty( key, val );
         }
      
         if ( size() > 6 )
         {
            huc.setDoOutput( true );
            huc.connect();
            DataOutputStream dos = new DataOutputStream
            (
               huc.getOutputStream()
            );
            for( int i=6; i<size(); i++ )
            {
               String data = getStringArg(i);
               System.err.println( "Writing " + data );
               dos.writeBytes( data );
            }
            dos.flush();
         }
         else
         {
            huc.connect();
         }

         BufferedReader rdr = new BufferedReader
         (
            new InputStreamReader( huc.getInputStream() )
         );
         
         String line;
         
         while( ( line = rdr.readLine() ) != null )
         {
            System.err.println( line );
            response.addElement( new JailAtom( line ) );
         }
      }
      catch( Exception e ) 
      {
         System.err.println( e );
         e.printStackTrace();
      }
      
      return response;
   }
   
   public static void main( String[] args )
   {
      JF_httpSend sender = new JF_httpSend();
      sender.addElement( new JailAtom( "hurlSend" ) );
      sender.addElement( new JailAtom( "POST" ) );
      sender.addElement( new JailAtom( "47.104.12.17" ) );
      sender.addElement( new JailAtom( "80" ) );
      sender.addElement( new JailAtom( "/prov/direct/loginServlet" ) );
      
      //?ims_domain=default&login=admin&password=admin" ) );
      
      JailHash props   = new JailHash();
      JailHash cookies = new JailHash();
      
      props.put( "ims_domain", "default" );
      props.put( "login",      "admin"   );
      props.put( "password",   "admin"   );
      
      sender.addElement( props   );
      sender.addElement( cookies );
      
      System.err.println( sender.applyCommand().toString() );
   }
}
