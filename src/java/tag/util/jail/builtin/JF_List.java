package tag.util.jail.builtin;

import java.util.*;
import tag.util.*;
import tag.util.jail.fw.*;
import tag.util.jail.*;

/**
*
*   This function creates a Jail List object.<br><br>
*
*   (List list)
*
*   It's similar to the quote() command in LISP, I think.
*/
public class JF_List extends JailFunction implements Culpable, Cloneable
{
   protected String[][] getSyntax()  { return JailFunction.NULLSYNTAX; }
   protected boolean evaluateArguments() { return true; }
   
   protected Culpable applyCommand()    
   {
      JailVector list = new JailVector( this );
      list.removeElement( list.firstElement() );
      return list;
   }
}
