package tag.util.jail.builtin;

import java.io.*;
import java.net.*;
import java.util.*;

import tag.util.*;
import tag.util.jail.fw.*;
import tag.util.jail.*;

/**
*
*   Fires up the UDP server.
*
*   (server port)
*
*/
public class JF_UdpServer extends JailFunction 
       implements Culpable, 
                  Cloneable
{
   protected String[][] getSyntax()  { return JailFunction.NULLSYNTAX; }
   protected boolean evaluateArguments() { return true; }
   
   protected Culpable applyCommand()    
   {
      int      port;
      Jail     jail     = Jail.getInstance();
      
      try
      {
         port = getIntArg(1);
         DatagramSocket server = new DatagramSocket( port );
         DatagramPacket pin    = new DatagramPacket( new byte[2048], 2048 );

         System.err.println( "UDP Server listening on port " + port );
      
         server.receive( pin );
         
         System.err.println( "Got packet." );
         
         String payload = new String( pin.getData() ).trim();
                  
         System.err.println( "Payload: " + payload );
         
         String response = jail.run( new StringReader( payload ) ).toString().trim();
         
         System.err.println( "\nResponse: " + response );
         
         DatagramPacket pout = new DatagramPacket( response.getBytes(), 
                                                   response.length(),
                                                   pin.getAddress(),
                                                   pin.getPort() );
         
         server.send( pout );                                            
      }
      catch( Exception e )    
      {
         e.printStackTrace();
      }
      
      return null;
   }
}
