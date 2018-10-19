package tag.util.jail.builtin;

import java.io.*;
import java.util.*;
import tag.util.*;
import tag.util.jail.fw.*;
import tag.util.jail.*;

/**
*
*   Starts up CLI mode.<br><br>
*
*   (cli)<br>
*   (cli prompt)<br>
*/
public class JF_cli extends JailFunction implements Culpable, Cloneable
{
   protected String[][] getSyntax()  { return JailFunction.NULLSYNTAX; }
   protected boolean evaluateArguments() { return true; }
   
   protected Culpable applyCommand()    
   {
      int patience = 0;
      
      String prompt = "> ";
      if ( size() > 1 )
      {
         prompt = ((Culpable)elementAt(1)).eval().toString();
      }
      
   	try
   	{
   	   BufferedReader br = new BufferedReader
   	   ( 
   	      new InputStreamReader( System.in )
   	   );
   	   
   	   System.err.println( "Command-Line Mode Active." );
   	   for/*ever*/(;;)
   	   {
   	   	System.err.print( prompt );
   		   String in = br.readLine();
   		  
   		   if ( in.equals( "help" ) )
   		   {
   		      System.err.println( "Functions:\n" );
               Enumeration cmds = Jail.getInstance().getRegistry();
   		      
   		      int col = 0;
   		      while( cmds.hasMoreElements() )
   		      {
   		         String cmd = cmds.nextElement().toString();
   		         System.err.print( cmd );
   		         for( int j=cmd.length(); j<19; j++ )
   		            System.err.print( ' ' );
   		            
   		         col++;
   		         if ( col % 4 == 0 )
   		            System.err.println();   		         
   		      }
   		   }
   		   else if ( in.equals( "continue" ) ) 
   		   {
   		   	System.err.println( "Command-Line Mode Terminated." );
   		   	break;
   		   }
   		   else if ( in.equals( "die" ) )
   		   {
   		      System.err.println( "Script execution terminated." );
   		      System.exit(0);
   		   }
   		   
   		   in = in.trim();
   		   
   		   if ( in.length() > 0 )
   		   {
   		      if ( in.startsWith("(") == false ) in = "(" + in;
   		      if ( in.endsWith(")")   == false ) in = in + ")";
   		      
               new JailInterpreter().interpret
               ( 
                  new JailParser( new StringReader( in ) ).parse()
               ); 
            }
            else
            {
               patience++;
               if ( patience >= 5 )
               {
                  System.err.println( "Command-Line Mode Active." );
                  patience = 0;
               }
            }
  	      }
  	   }
  	   catch( Exception e )
  	   {
  	   	e.printStackTrace();
  	   }

   	return null;
   }
}
