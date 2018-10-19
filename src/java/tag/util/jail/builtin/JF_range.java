package tag.util.jail.builtin;

import java.util.*;
import tag.util.*;
import tag.util.jail.fw.*;
import tag.util.jail.*;

/**
*
*   Returns a vector of numbers.
*
*   (range first_number last_number step)
*
*/
public class JF_range extends JailFunction implements Culpable, Cloneable
{
   protected String[][] getSyntax()  { return JailFunction.NULLSYNTAX; }
   protected boolean evaluateArguments() { return true; }
   
   protected Culpable applyCommand()    
   {
      int start = getIntArg(1);
      int end   = getIntArg(2);
      int step  = getIntArg(3);
      
      JailVector out = new JailVector();
      
      for( int i=start; i<=end; i+=step )
         out.addElement( new JailAtom( i ) );
         
      return out;
   }
}
