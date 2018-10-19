package tag.util.jail.builtin;

import java.util.*;
import tag.util.*;
import tag.util.jail.fw.*;
import tag.util.jail.*;

/**
*
*   Returns JF_eq.TRUE if the element has no entry in the symbol table
*   (= not defined).  IF no element is provided, the $_ pronoun is assumed.
*
*/
public class JF_no extends JailFunction implements Culpable, Cloneable
{
   protected String[][] getSyntax()  { return JailFunction.NULLSYNTAX; }
   protected boolean evaluateArguments() { return true; }
   
   protected Culpable applyCommand()    
   {
      String key = null;
      
      if ( size() > 1 )
         key = getArg(1).toString();
      else
         key = "$_";
               
      if ( Jail.getContext().containsKey( key ) )
         return JF_eq.FALSE;
      else
         return JF_eq.TRUE;
   }
}
