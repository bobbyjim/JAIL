package tag.util.jail.builtin;

import java.io.*;
import java.util.*;
import tag.util.*;
import tag.util.jail.fw.*;
import tag.util.jail.*;


/************************************************************************
*
*   JF_httpFormat: builds an HTTP request
*
*   Example:
*
*   (http.format POST message arglist headers...)
*
************************************************************************/
public class JF_httpFormat extends JailFunction implements Culpable, Cloneable
{
   protected String[][] getSyntax()  { return JailFunction.NULLSYNTAX; }

   protected boolean evaluateArguments() 
   {
      return true;
   }
   
   protected Culpable applyCommand()    
   {
      String   method   = getStringArg(1).toUpperCase();
      String   message  = getStringArg(2);
      
      if ( size() > 3 ) try
      {
         JailVector args = (JailVector) getArg(3).eval();
         JF_httpProperties props = new JF_httpProperties();
         props.addMembers( new JailAtom( "http.props" ) );
         props.addMembers( args );
         message += props.eval();
      }
      catch( Exception e )
      {
         System.err.println( "HttpFormat error: " + getArg(3) + " is not a list.\n" );
         System.err.println( getArg(3).getClass().getName() + ":" + getArg(3).eval().getClass().getName() );
      }
      
      
      if ( message.endsWith( "\n" ) )
         System.err.println( "Warning: HTTP URL should not end with newline.  Trimming." );
      
      message = message.trim();
      message = method + " " + message + " HTTP/1.0\r\n";
                  
      for( int i=4; i<size(); i++ )
         message += ((Culpable) elementAt(i)).eval().toString( "" ) + "\r\n"; // no delimiters...

      message += "\r\n\r\n";
      
      return new JailAtom( message );
   }
}
