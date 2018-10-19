package tag.util.jail.fw;

import java.io.*;
import java.net.*;
import java.util.*;

import tag.util.jail.builtin.*;
import tag.util.jail.*;

public class Client
{
   public static void main( String[] args ) throws Exception
   {
      String host  = args[0];
      int    port  = Integer.parseInt( args[1] );
      Query  query = new Query();
      
      query.setQueryData( new String[] { "traffic.scenarios" } );
      
      Hashtable response = (Hashtable) query.getResponse( host, port );
      
      Enumeration keys = response.keys();
      while( keys.hasMoreElements() )
      {
         String    scenarioName = (String)    keys.nextElement().toString();
         Hashtable scenarioData = (Hashtable) response.get( scenarioName );
         
         System.err.println( "Scenario  : " + scenarioName );
         System.err.println( "   hosts  : " + scenarioData.get( "hosts"   ) );
         System.err.println( "   scripts: " + scenarioData.get( "scripts" ) );
         System.err.println( "   users  : " + scenarioData.get( "users"   ) );
         System.err.println( "   epochs : " + scenarioData.get( "epochs"  ) );
         System.err.println( "   pause  : " + scenarioData.get( "pause"   ) );
         
         System.err.println();
      }
   }
}
