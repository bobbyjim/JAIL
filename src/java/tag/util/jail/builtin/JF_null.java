package tag.util.jail.builtin;

import java.util.*;
import tag.util.*;
import tag.util.jail.fw.*;
import tag.util.jail.*;

/**
*
*   Returns 'true' if arg is an empty vector.
*
*   (define null (x) (equals x ()))   
*
*/
public class JF_null extends JailFunction implements Culpable, Cloneable
{
   protected String[][] getSyntax()  { return JailFunction.NULLSYNTAX; }
   protected boolean evaluateArguments() { return true; }
   
   protected Culpable applyCommand()    
   {
      Culpable arg = getArg(1);
      
      if ( arg instanceof JailVector
        && ((JailVector)arg).size() == 0 )
         return JF_eq.TRUE;
      else
         return JF_eq.FALSE;
   }
}
