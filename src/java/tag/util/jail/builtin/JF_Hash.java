package tag.util.jail.builtin;

import java.util.*;
import tag.util.*;
import tag.util.jail.fw.*;
import tag.util.jail.*;

/**
*
*   This function creates a Jail Hash object.<br><br>
*
*   (Hash (values))
*
*/
public class JF_Hash extends JailFunction implements Culpable, Cloneable
{
   protected String[][] getSyntax()  { return JailFunction.NULLSYNTAX; }
   protected boolean evaluateArguments() { return true; }
   
   protected Culpable applyCommand()    
   {
      if ( size() == 2 )
         return new JailHash( (JailVector) elementAt(1) );
      else
         return new JailHash( this );
   }
}
