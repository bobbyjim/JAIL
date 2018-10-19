package tag.util.jail.builtin;

import java.util.*;
import tag.util.*;
import tag.util.jail.fw.*;
import tag.util.jail.*;

/**
*
*   Conditional execution structure.<br><br>
*
*   (or expr expr expr) <br><br>
*
*   Stops at and returns the value of the first 
*   non-false expression.
*
*/
public class JF_or extends JailFunction implements Culpable, Cloneable
{
   protected String[][] getSyntax()  { return JailFunction.NULLSYNTAX; }
   protected boolean evaluateArguments() { return true; }
   
   protected Culpable applyCommand()    
   {
      Culpable out  = new JailAtom( "" );
      boolean  stop = false;
      
      for( int i=1; stop == false && i<size(); i++ )
      {
         String tres = getStringArg(i);
         stop = true;
         if ( tres.equals( ""      ) 
           || tres.equals( "0"     )
           || tres.equals( "0.0"   )
           || tres.equals( "false" ) )
            stop = false;
         else
            out = new JailAtom( tres );
      }
      
      return out;
   }
}
