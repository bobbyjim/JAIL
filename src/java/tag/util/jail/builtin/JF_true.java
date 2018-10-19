package tag.util.jail.builtin;

import java.util.*;
import tag.util.*;
import tag.util.jail.fw.*;
import tag.util.jail.*;

/**
*
*   Returns 'true' if arg is true.
*
*   (true arg)   
*
*/
public class JF_true extends JF_false implements Culpable, Cloneable
{
   protected String[][] getSyntax()  { return JailFunction.NULLSYNTAX; }
   protected boolean evaluateArguments() { return true; }
   
   protected Culpable applyCommand()    
   {
      if ( super.applyCommand() == JF_eq.TRUE )
         return JF_eq.FALSE;
      else
         return JF_eq.TRUE;
   }
}
