package tag.util.jail.builtin;

import java.util.*;
import tag.util.*;
import tag.util.jail.fw.*;
import tag.util.jail.*;

/**
*
*   This function returns a vector of keys into the hashtable given.<br><br>
*
*   (keys hash)
*
*/
public class JF_keys extends JailFunction implements Culpable, Cloneable
{
   protected String[][] getSyntax()  { return JailFunction.NULLSYNTAX; }
   protected boolean evaluateArguments() { return true; }
   
   protected Culpable applyCommand()    
   {
      JailHash hash = (JailHash) getArg(1).eval();
      return new JailVector( hash.keys() );
   }
}
