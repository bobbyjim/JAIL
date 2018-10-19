package tag.util.jail.builtin;

import java.util.*;
import tag.util.*;
import tag.util.jail.fw.*;
import tag.util.jail.*;

/**
*
*   This function creates a Jail Atom object.<br><br>
*   
*   (Atom 'value')
*
*/
public class JF_Atom extends JailFunction implements Culpable, Cloneable
{
   protected String[][] getSyntax()  { return JailFunction.NULLSYNTAX; }
   protected boolean evaluateArguments() { return true; }
   
   protected Culpable applyCommand()    
   {
      return new JailAtom( ((Culpable) elementAt(1)).eval().toString() );
   }
}
