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
*   (http.send method host port path [properties [headers]])<br>
*
*/
public class JF_httpSend extends JailFunction implements Culpable, Cloneable
{
   protected String[][] getSyntax()  { return JailFunction.NULLSYNTAX; }
   protected boolean evaluateArguments() { return true; }
   
   protected Culpable applyCommand()    
   {
      JailAtom method   = getAtomArg(1);
      JailAtom host     = getAtomArg(2);
      JailAtom port     = getAtomArg(3);
      JailAtom path     = getAtomArg(4);
      
      JF_httpFormat fmt = new JF_httpFormat();
      fmt.addMembers( new JailAtom( "http.fmt" ) );
      fmt.addMembers( method  );
      fmt.addMembers( path    );
      
      for( int i=5; i<size(); i++ )
      {
         fmt.addElement( getVectorArg(i) );  // props and headers
      }

      JailAtom msg = (JailAtom) fmt.eval();
      
      JF_tcpSend tcp = new JF_tcpSend();
      tcp.addMembers( new JailAtom( "tcp.out" ) );
      tcp.addMembers( host );
      tcp.addMembers( port );
      tcp.addMembers( msg  );

      if ( Jail.getInstance().getDebug() )
      {
         System.err.println( "http.out: http format: " + msg );
         System.err.println( "http.out: tcp  format: " + tcp );
      }
      
      return tcp.eval();
      
      /*
      String   method   = getStringArg(1);
      String   host     = getStringArg(2);
      int      port     = getIntArg(3);
      String   path     = getStringArg(4);
      JailHash props    = null;
      JailHash cookies  = null;
      
      if ( size() > 5 )  props   = getHashArg(5);
      if ( size() > 6 )  cookies = getHashArg(6);
      
      JailVector out = new JailVector();
      
      String message = "http://" + host + ":" + port + path;

      if ( Jail.getInstance().getDebug() )      
      {
         System.err.println( "Args: " + size() );
         
         System.err.println( "Method    : " + method ); 
         System.err.println( "Host:port : " + host + ":" + port );
         System.err.println( "Path      : " + path );
         System.err.println( "Properties: " + props );
         System.err.println( "Cookies   : " + cookies );
      }
      
      
      try
      {
         HttpMessage   msg = new HttpMessage( new URL( "HTTP", host, port, path ) );
         URLConnection con;
   
         if ( method.equalsIgnoreCase( "POST" ))
            con = msg.sendPostMessage( props, cookies );
         else
            con = msg.sendGetMessage( props, cookies );
        
         JailVector headers = new JailVector();
         headers.addElement( new JailAtom( con.getHeaderField( 0 ) ) );
         int i=1;
         while( con.getHeaderFieldKey( i ) != null )
            headers.addElement( new JailAtom( con.getHeaderFieldKey(i) + ": " + con.getHeaderField( i++ ) ) );

         out.addElement( headers );
         
         InputStream   is  = con.getInputStream();
         
         byte[] buffer = new byte[ is.available() ];
         is.read( buffer );
         is.close();
   
         String buf = new String( buffer );
         StringTokenizer tok = new StringTokenizer( buf, "\n" );
         tok.nextToken(); // blank line
         tok.nextToken(); // blank line
         tok.nextToken(); // blank line
         
         JailVector body = new JailVector();
         
         while( tok.hasMoreTokens() )
            body.addElement( new JailAtom( tok.nextToken() ) );
            
         out.addElement( body );
      }
      catch( Exception e )
      {
         e.printStackTrace();
      }
      
      return out;
      */
   }
   
   public static void main( String[] args )
   {
      JF_httpSend sender = new JF_httpSend();
      sender.addElement( new JailAtom( "httpSend" ) );
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
