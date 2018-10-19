package tag.util.jail.builtin;

import java.util.*;
import tag.util.*;
import tag.util.jail.fw.*;
import tag.util.jail.*;

/**
*
*   Returns "true" if the argument is zero, "false" otherwise.
*
*/
public class JF_zero extends JailFunction implements Culpable, Cloneable
{
   protected String[][] getSyntax()  { return JailFunction.NULLSYNTAX; }
   protected boolean evaluateArguments() { return true; }
   
   protected Culpable applyCommand()    
   {
      if ( size() == 1 )
         addElement( new JailAtom( "$_" ) );
      
      if ( getStringArg(1).equals( "0" ) )
         return JF_eq.TRUE;
      else
         return JF_eq.FALSE;
   }
}
