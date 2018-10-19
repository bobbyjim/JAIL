package tag.util.jail.builtin;

import java.util.*;
import tag.util.*;
import tag.util.jail.fw.*;
import tag.util.jail.*;

/**
*
*   Returns a list of indexes into the provided vector of positions
*   where the indicated pattern occurs (or the empty vector if there 
*   are no such positions).
*
*   (grep pattern list)
*   (grep pattern)      // implies the list is in $_
*
*/
public class JF_grep extends JailFunction implements Culpable, Cloneable
{
   protected String[][] getSyntax()  { return JailFunction.NULLSYNTAX; }
   protected boolean evaluateArguments() { return true; }
   
   protected Culpable applyCommand()    
   {
      String pattern = getStringArg(1);
      
      if ( size() == 2 )
         addElement( new JailAtom( "$_" ) );
      
      JailVector list = getVectorArg(2);
      JailVector out  = new JailVector();
      
      for( int i=0; i<list.size(); i++ )
         if ( list.elementAt(i).toString().indexOf( pattern ) > -1 )
            out.addElement( new JailAtom( i ) );
            
      return out;
   }
}
