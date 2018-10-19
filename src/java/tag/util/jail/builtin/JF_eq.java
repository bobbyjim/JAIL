package tag.util.jail.builtin;

import java.util.*;
import tag.util.*;
import tag.util.jail.fw.*;
import tag.util.jail.*;

/**
*
*   Returns "true" if the arguments are all equal, "false" otherwise.
*
*/
public class JF_eq extends JailFunction implements Culpable, Cloneable
{
   public static Culpable TRUE  = new JailAtom( "true" );
   public static Culpable FALSE = new JailVector();
   
   protected String[][] getSyntax()  { return JailFunction.NULLSYNTAX; }
   protected boolean evaluateArguments() { return true; }
   
   protected Culpable applyCommand()    
   {
   	return compare();
   }
   
   Culpable compare()
   {
      String first = ((Culpable) elementAt(1)).eval().toString();
      Culpable result = JF_eq.TRUE;
      
      for( int i=2; i<size(); i++ )
      {
         Culpable next = ((Culpable) elementAt(i)).eval();         
         
         if ( first.equals( next.toString() ) == false )
         {
            result = JF_eq.FALSE;
            break;
         }
      }
      
      return result;
   }
}
