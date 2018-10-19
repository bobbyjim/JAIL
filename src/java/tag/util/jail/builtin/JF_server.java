package tag.util.jail.builtin;

import java.io.*;
import java.net.*;
import java.util.*;

import tag.util.*;
import tag.util.jail.fw.*;
import tag.util.jail.*;

/**
*
*   Fires up the TCP server.
*
*   (server port)
*
*/
public class JF_server extends JailFunction implements Culpable, Cloneable
{
   protected String[][] getSyntax()  { return JailFunction.NULLSYNTAX; }
   protected boolean evaluateArguments() { return true; }
   
   protected Culpable applyCommand()    
   {
      int      port;
      Socket   client;
      Query    query    = null;
      Culpable response = null;
      
      try
      {
         port   = getIntArg(1);

         ServerSocket listener = new ServerSocket( port );

         System.err.println( "Listening on port " + port );
      
         client = listener.accept();

         System.err.println( "Accepted connection." );
            
         ObjectInputStream querystream = new ObjectInputStream
         (
            client.getInputStream()
         );
         
         query    = (Query) querystream.readObject();
         
         System.err.println( "Query: " + query.toString() );
         
         response = query.eval(); // handle()
         
         System.err.println( "\nResponse: " + response.toString() );
         
         ObjectOutputStream responsestream = new ObjectOutputStream
         (
            client.getOutputStream()
         );
         
         responsestream.writeObject( response );
         
         listener.close();
         listener = null;
         client   = null;
      }
      catch( Exception e )    
      {
         System.err.println( "TCP SERVER SYNOPSIS: server <port>" );
      }
      
      return response;
   }
}
