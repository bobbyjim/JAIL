package tag.util.jail.builtin;

import java.util.*;
import tag.util.*;
import tag.util.jail.fw.*;
import tag.util.jail.*;

/**
*
*   Issues System.exit(0);
*
*/
public class JF_exit extends JailFunction implements Culpable, Cloneable
{
   protected String[][] getSyntax()  { return JailFunction.NULLSYNTAX; }
   protected boolean evaluateArguments() { return true; }
   
   protected Culpable applyCommand()    
   {
      int retval = 0;
      
      if ( size() > 1 ) try
      {
         retval = Integer.parseInt( ((Culpable) elementAt(1)).eval().toString() );
      }
      catch( Exception e ) 
      {
         retval = -1;
      }
      
      System.exit( retval );
      return null;
   }
}
