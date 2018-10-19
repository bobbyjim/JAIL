package tag.util.jail.builtin;

import java.io.*;
import java.net.*;
import java.util.*;
import tag.util.*;
import tag.util.jail.fw.*;
import tag.util.jail.*;

/**
*
*   JF_tcpSend: sends a TCP message and returns the response.<br><br>
*
*   Example:<br><br>
*
*   (tcpSend host port message...)<br>
*
*/
public class JF_tcpSend extends JailFunction implements Culpable, Cloneable
{
   protected String[][] getSyntax()  { return JailFunction.NULLSYNTAX; }
   protected boolean evaluateArguments() { return true; }
   
   protected Culpable applyCommand()    
   {
      String host     = getStringArg(1);
      int    port     = getIntArg(2);
      String message  = "";
                  
      for( int i=3; i<size(); i++ )
      {
         String msg = getStringArg(i).trim();
         message += msg + "\r\n"; // no delimiters...
      }
       
      message += "\r\n";

      return send( host, port, message );
   }


   public JailVector send( String host, int port, String message )
   {
      JailVector response = new JailVector();
      try
      {
         Socket           conn = new Socket( host, port );
         DataOutputStream dout = new DataOutputStream( conn.getOutputStream() );
         
         dout.writeBytes( message );
         dout.flush();
         
         BufferedReader in = new BufferedReader( new InputStreamReader( conn.getInputStream() ) );

         String line = in.readLine();
         
         while( line != null )
         {
            //System.err.println( "JF_tcpSend:line=" + line );
            //response.addElement( new JailAtom( URLDecoder.decode( line, "UTF-8" ) ) );
            response.addElement( new JailAtom( line ) );
            line = in.readLine();
         }
         
      }
      catch( Exception ee )
      {
         //Log.getInstance().log( "TCP Send: " + ee );
      }    
      return response;
   }
}
