package tag.util.jail.builtin;

import java.io.*;
import java.net.*;
import java.util.*;
import tag.util.*;
import tag.util.jail.fw.*;
import tag.util.jail.*;

/**
*
*   JF_httpProperties: builds a property string for an HTTP message.<br><br>
*
*   Example:<br><br>
*
*   (http.props (login admin) (password admin))<br>
*
*   Returns the string:<br><br>
*
*   ?login=admin&password=admin
*
*/
public class JF_httpProperties extends JailFunction implements Culpable, Cloneable
{
   protected String[][] getSyntax()  { return JailFunction.NULLSYNTAX; }
   protected boolean evaluateArguments() { return true; }
   
   protected Culpable applyCommand()    
   {
      String props = "";

      if ( size() > 1 )
         props = kvPair( "?", 1 ); 
         
      for( int i=2; i<size(); i++ )
         props += kvPair( "&", i );
         
      return new JailAtom( props );
   }
   
   private String kvPair( String prefix, int index )
   {
      String out = "";
      try
      {
         JailVector vec = getVectorArg( index );
         out = prefix
              + vec.elementAt(0).toString() + "=" 
              + vec.elementAt(1).toString();
      }
      catch( Exception e )
      {
         System.err.println( "ERROR: Http property header constructor: element \'" 
                           + getArg(index).toString() 
                           + "\' does not evaluate to a list.\n" );
      }
      return out;
   }
}
