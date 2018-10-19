package tag.util.jail.builtin;

import tag.util.jail.fw.*;
import tag.util.jail.*;

/**
*
*   Decrements the variables in the list.<br><br>
*
*   (dec list)    // list incrementer
*   (dec)         // implicit operator "$_"<br>
*
*/
public class JF_dec extends JF_inc implements Culpable, Cloneable
{
   public JF_dec()
   {
      delta.init( -1 );
   }
}
