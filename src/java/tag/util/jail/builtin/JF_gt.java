package tag.util.jail.builtin;

import java.util.*;
import tag.util.*;
import tag.util.jail.fw.*;
import tag.util.jail.*;

/**
*
*  (gt x y)
*
*/
public class JF_gt extends JailFunction implements Culpable, Cloneable
{
   protected String[][] getSyntax()  { return JailFunction.NULLSYNTAX; }
   protected boolean evaluateArguments() { return true; }
   
   protected Culpable applyCommand()    
   {
      int one = getIntArg(1);
      int two = getIntArg(2);
      
      if ( one > two )
         return JF_eq.TRUE;
      else
         return JF_eq.FALSE;
   }
}
