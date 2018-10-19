package tag.util.jail.builtin;

import java.util.*;
import tag.util.*;
import tag.util.jail.fw.*;
import tag.util.jail.*;

/**
*
*   Conditional execution structure.<br><br>
*
*   (if (expr) (true-expr) (false-expr))
*
*/
public class JF_if extends JailFunction implements Culpable, Cloneable
{
   protected String[][] getSyntax()  { return JailFunction.NULLSYNTAX; }
   protected boolean evaluateArguments() { return true; }
   
   protected Culpable applyCommand()    
   {
      Culpable out  = new JailAtom( "" );      
      String   tres = getStringArg(1);
      
      if ( tres.equals( ""      ) 
        || tres.equals( "0"     )
        || tres.equals( "0.0"   )
        || tres.equals( "false" ) 
        || tres.equals( "()\n"  ) )
      {
         if ( size() > 2 )
            out = ((Culpable) elementAt(3)).eval();
      }
      else
      {
         out = ((Culpable) elementAt(2)).eval();
      }
      
      return out;
   }
}
