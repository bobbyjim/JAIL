package tag.util.jail.builtin;

import java.util.*;
import tag.util.*;
import tag.util.jail.fw.*;
import tag.util.jail.*;

/**
*
*  This function sets debug mode.
*
*  (debug)       // returns mode as JF_eq.TRUE or JF_eq.FALSE.
*  (debug true)  // turns on debug mode
*  (debug false) // turns off debug mode
*
*/
public class JF_debug extends JailFunction implements Culpable, Cloneable
{
   protected String[][] getSyntax()  { return JailFunction.NULLSYNTAX; }
   protected boolean evaluateArguments() { return true; }

   protected Culpable applyCommand()    
   {
      Jail     jail = Jail.getInstance();
      Culpable out;
      
      if ( size() == 1 )
      {
         if ( jail.getDebug() )
            out = JF_eq.TRUE;
         else
            out = JF_eq.FALSE;
      }
      else
      {
         out = getArg(1).eval();  // 2nd element of vector
         String mode = out.toString();

         if ( mode.equals( JF_eq.TRUE.toString() ) )
            jail.setDebug( true );
         else
            jail.setDebug( false );
      }
         
      return out;
   }
}