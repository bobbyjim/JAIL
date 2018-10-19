package tag.util.jail.builtin;

import java.util.*;
import tag.util.*;
import tag.util.jail.fw.*;
import tag.util.jail.*;

/**
*
*   Returns 'true' if arg is false.
*
*   (false arg)   
*
*/
public class JF_false extends JailFunction implements Culpable, Cloneable
{
   protected String[][] getSyntax()  { return JailFunction.NULLSYNTAX; }
   protected boolean evaluateArguments() { return true; }
   
   protected Culpable applyCommand()    
   {
      String arg = getStringArg(1);
      
      if ( arg.equals( ""      ) 
        || arg.equals( "0"     ) 
        || arg.equals( "0.0"   ) 
        || arg.equals( "false" )  
        || arg.equals( "()"    ) )
         return JF_eq.TRUE;
      else
         return JF_eq.FALSE;
   }
}
