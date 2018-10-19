package tag.util.jail.builtin;

import java.util.*;
import tag.util.*;
import tag.util.jail.fw.*;
import tag.util.jail.*;

/**
*
*   Divides a number by all following numbers.<br><br>
*
*   (div n m)     - returns n/m<br>
*   (div n m o p) - returns n/m/o/p<br>
*
*/
public class JF_div extends JailFunction implements Culpable, Cloneable
{
   protected String[][] getSyntax()  { return JailFunction.NULLSYNTAX; }
   protected boolean evaluateArguments() { return true; }
   
   protected Culpable applyCommand()    
   {
      JailAtom n = (JailAtom) ((Culpable) elementAt(1)).eval();
      double num = n.d;
      
      for( int i=2; i<size(); i++ )
      {
         JailAtom m = (JailAtom) ((Culpable) elementAt(i)).eval();
         num /= m.d;
      }
      
      return new JailAtom( num );
   }
}
