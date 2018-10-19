package tag.util.jail.builtin;

import java.util.*;
import tag.util.*;
import tag.util.jail.fw.*;
import tag.util.jail.*;

/**
*
*  This function executes its arguments, returning the last value.
*
*/
public class JF_do extends JF_join implements Culpable, Cloneable
{
   protected Culpable applyCommand()
   {
      Culpable out = null;
      
      for( int i=1; i<size(); i++ )
         out = getArg(i).eval();
      
      return out;
   }
}