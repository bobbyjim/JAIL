package tag.util.jail.builtin;

import java.util.*;
import tag.util.*;
import tag.util.jail.fw.*;
import tag.util.jail.*;

/**
*
*   Eternal execution structure.<br><br>
*
*   (forever body)
*
*/
public class JF_forever extends JailFunction implements Culpable, Cloneable
{
   protected String[][] getSyntax()  { return JailFunction.NULLSYNTAX; }
   protected boolean evaluateArguments() { return true; }
   
   protected Culpable applyCommand()    
   {
      Culpable body = getArg(1);

      while( body != null )
         ((Culpable) body.clone()).eval();
    
      return JF_eq.TRUE;
   }
}
