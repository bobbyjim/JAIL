package tag.util.jail.builtin;

import java.util.*;
import tag.util.*;
import tag.util.jail.fw.*;
import tag.util.jail.*;

/**
*
*   Multiplies numbers together.<br><br>
*
*   (mul n m)     - returns n*m<br>
*   (mul n m o p) - returns n*m*o*p<br>
*
*/
public class JF_mul extends JailFunction implements Culpable, Cloneable
{
   protected String[][] getSyntax()  { return JailFunction.NULLSYNTAX; }
   protected boolean evaluateArguments() { return true; }
   
   protected Culpable applyCommand()    
   {
      double mul = 1.0;
      for( int i=1; i<size(); i++ )
      {
         JailAtom n = (JailAtom) ((Culpable) elementAt(i)).eval();
         mul *= n.d;
      }
      
      return new JailAtom( mul );
   }
}
