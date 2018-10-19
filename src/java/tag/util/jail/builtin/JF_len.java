package tag.util.jail.builtin;

import java.util.*;
import tag.util.*;
import tag.util.jail.fw.*;
import tag.util.jail.*;

/**
*
*   Returns the length of the JailAtom or size of the JailVector.<br>
*   This quantity is returned in a JailAtom.
*
*/
public class JF_len extends JailFunction implements Culpable, Cloneable
{
   protected String[][] getSyntax()  { return JailFunction.NULLSYNTAX; }
   protected boolean evaluateArguments() { return true; }

   
   protected Culpable applyCommand()    
   {
      JailAtom out = new JailAtom(0);
      
      Culpable elem = (Culpable) elementAt(1);
      
      if ( elem instanceof JailAtom )
         out = new JailAtom( elem.toString().length() );
      
      if ( elem instanceof JailVector )
         out = new JailAtom( ((JailVector)elem).size() );
         
      return out;
   }
}
