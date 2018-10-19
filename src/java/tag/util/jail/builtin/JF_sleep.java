package tag.util.jail.builtin;


import java.util.*;
import tag.util.*;
import tag.util.jail.fw.*;
import tag.util.jail.*;

/**
*
*   Sleeps for the given number of milliseconds.
*
*/
public class JF_sleep extends JailFunction implements Culpable, Cloneable
{
   protected String[][] getSyntax()  { return JailFunction.NULLSYNTAX; }
   protected boolean evaluateArguments() { return true; }
   
   protected Culpable applyCommand()    
   {
      try
      {
         Thread.sleep( getIntArg(1) );
      }
      catch( Exception e ) {}
      
      return new JailAtom( "" );
   }
}
