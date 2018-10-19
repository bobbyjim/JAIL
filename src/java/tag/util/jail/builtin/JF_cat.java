package tag.util.jail.builtin;

import java.util.*;
import tag.util.*;
import tag.util.jail.fw.*;
import tag.util.jail.*;

/**
*
*  This function catenates its arguments into a string.
*
*/
public class JF_cat extends JF_join implements Culpable, Cloneable
{
   protected Culpable applyCommand()    
   {
      String out = "";
      
      for( int i=1; i<size(); i++ )
         out += ((Culpable)elementAt(i)).eval().toString();
      
      return new JailAtom( out );
   }
}