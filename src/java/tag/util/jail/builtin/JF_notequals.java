package tag.util.jail.builtin;

import java.util.*;
import tag.util.*;
import tag.util.jail.fw.*;
import tag.util.jail.*;

/**
*
*   Returns "1" if ONE OR MORE arguments are different.
*   Returns "0" if ALL arguments are the same.
*   This is basically the numeric opposite of JF_equals.
*
*/
public class JF_notequals extends JF_eq implements Culpable, Cloneable
{
   protected Culpable applyCommand()    
   {
      if ( compare() == JF_eq.TRUE )
         return JF_eq.FALSE;
      else
         return JF_eq.TRUE;
   }
}
