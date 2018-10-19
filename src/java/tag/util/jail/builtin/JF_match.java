package tag.util.jail.builtin;

import java.util.*;
import tag.util.*;
import tag.util.jail.fw.*;
import tag.util.jail.*;

/**
*
*   Looks for a simple pattern in a string:<br><br>
*
*   (match rexp string)<br><br>
*
*   If the pattern is found, it is returned.  Otherwise the empty string<br>
*   is returned.<br><br>
*
*   Examples:<br><br>
*
*   (match ^foo  string)  - looks for 'foo' at the start of the string.<br>
*   (match foo$  string)  - looks for 'foo' at the end of the string.<br>
*   (match ^foo$ string)  - matches 'foo' exactly.<br>
*
*/
public class JF_match extends JailFunction implements Culpable, Cloneable
{
      final int NONE = 0;
      final int HEAD = 1;
      final int TAIL = 2;
      final int BOTH = 3;

   protected String[][] getSyntax()  { return JailFunction.NULLSYNTAX; }
   protected boolean evaluateArguments() { return true; }
   
   protected Culpable applyCommand()    
   {
      String rexp    = ((Culpable) elementAt(1)).eval().toString();
      String string  = ((Culpable) elementAt(2)).eval().toString();
      
      int    size   = rexp.length();
      int    offset = 0;
      
      
      boolean found = false;
      
      JailAtom out = null;
      
      int anchor = NONE;
      
      if ( rexp.startsWith( "^" ) )
      {
         anchor += HEAD;
         offset = 1;
      }
      
      if ( rexp.endsWith( "$" ) ) 
      {
         anchor += TAIL;
         size--;
      }
      
      rexp = rexp.substring( offset, size );

      //System.err.println( "rexp=" + rexp );
      //System.err.println( "string=" + string );
      
      switch( anchor )
      {
         case NONE: if ( string.indexOf( rexp ) >= 0 ) found = true; break;
         case HEAD: if ( string.startsWith( rexp ) )   found = true; break;
         case TAIL: if ( string.endsWith( rexp ) )     found = true; break;
         case BOTH: if ( string.equals( rexp ) )       found = true; break;
      }
    
      if ( found )
         out = new JailAtom( rexp );
      else
         out = new JailAtom( "" );
           
      return out;
   }
}
