package tag.util.jail.builtin;

import java.io.*;
import java.net.*;
import java.util.*;

import tag.util.*;
import tag.util.jail.fw.*;
import tag.util.jail.*;

/**
*
*   Sends a UDP packet and returns response.
*
*   (udp.send host port msg)
*
*/
public class JF_UdpSend extends JF_tcpSend 
       implements Culpable, 
                  Cloneable
{
   protected String[][] getSyntax()  { return JailFunction.NULLSYNTAX; }
   protected boolean evaluateArguments() { return true; }
   
   public JailVector send( String host, int port, String message )
   {   
      JailVector out = null;
      try
      {
         InetAddress    addr   = InetAddress.getByName( host );
         DatagramSocket server = new DatagramSocket();         
         DatagramPacket pout   = new DatagramPacket( message.getBytes(), 
                                                     message.length(),
                                                     addr,
                                                     port );
                                                     
         System.err.println( "Sending to " + addr + ":" + port + ":" );
         System.err.println( message );
         
         server.send( pout );
         
         DatagramPacket pin   = new DatagramPacket( new byte[2048], 2048 );
         server               = new DatagramSocket();
         server.receive( pin );
         
         String response = new String( pin.getData() ).trim();
         out = new JailVector();
         out.addElement( new JailAtom( response ) );
      }
      catch( Exception e )    
      {
         e.printStackTrace();
      }
      
      return out;
   }
}
