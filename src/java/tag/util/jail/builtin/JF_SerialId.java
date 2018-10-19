package tag.util.jail.builtin;

import java.util.*;
import tag.util.*;
import tag.util.jail.fw.*;
import tag.util.jail.*;

/**
*
*   Returns a unique ID number, starting from the current epoch timestamp.
*
*/
public class JF_SerialId extends JailFunction implements Culpable, Cloneable
{
      static long id = new Date().getTime();
      
   protected String[][] getSyntax()  { return JailFunction.NULLSYNTAX; }
   protected boolean evaluateArguments() { return true; }
   
   protected Culpable applyCommand()    
   {
      return new JailAtom( id++ );
   }
}
