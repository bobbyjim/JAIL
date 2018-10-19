package tag.util.jail.builtin;

import java.util.*;
import tag.util.*;
import tag.util.jail.fw.*;
import tag.util.jail.*;

/**
*
*   Conditional execution structure.<br><br>
*
*   (while expr body)
*
*/
public class JF_while extends JailFunction implements Culpable, Cloneable
{
   protected String[][] getSyntax()  { return JailFunction.NULLSYNTAX; }
   protected boolean evaluateArguments() { return true; }
   
   protected Culpable applyCommand()    
   {
      Culpable test = getArg(1);
      Culpable body = getArg(2);
      Culpable out  = new JailAtom( "" );

      String tres = ((Culpable)test.clone()).eval().toString();

      while (  tres.equals( ""      ) == false
            && tres.equals( "0"     ) == false
            && tres.equals( "0.0"   ) == false
            && tres.equals( "false" ) == false 
            && tres.equals( "()"    ) == false )
      {
         Culpable code = (Culpable) body.clone();         
         Culpable expr = (Culpable) test.clone();
         
         out  = code.eval();
         tres = expr.eval().toString();
      }
    
      return out;
   }
}
