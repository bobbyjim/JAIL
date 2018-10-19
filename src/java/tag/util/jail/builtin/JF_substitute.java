package tag.util.jail.builtin;

import java.util.*;
import tag.util.*;
import tag.util.jail.fw.*;
import tag.util.jail.*;

/**
*
*   Replaces a simple pattern in a string:<br><br>
*
*   (subst pattern replacement string)<br><br>
*
*   If the pattern is found, it is returned.  Otherwise the empty string<br>
*   is returned.<br><br>
*
*   Examples:<br><br>
*
*   (subst ^foo  bar string)  - replaces 'foo' at the start of the string with 'bar'.<br>
*   (subst foo$  bar string)  - replaces for 'foo' at the end of the string with 'bar'.<br>
*   (subst ^foo$ bar string)  - replaces 'foo' exactly with 'bar'.<br>
*
*/
public class JF_substitute extends JailFunction implements Culpable, Cloneable
{
      final int NONE = 0;
      final int HEAD = 1;
      final int TAIL = 2;
      final int BOTH = 3;

   protected String[][] getSyntax()  { return JailFunction.NULLSYNTAX; }

   protected boolean evaluateArguments() { return true; }
   
   protected Culpable applyCommand()    
   {
      String rexp        = ((Culpable) elementAt(1)).eval().toString();
      String replacement = ((Culpable) elementAt(2)).eval().toString();
      String string      = ((Culpable) elementAt(3)).eval().toString();
      
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

      switch( anchor )
      {
         case NONE: while( string.indexOf( rexp ) >= 0 ) 
                    {
//                       System.err.println( string );
                       int index = string.indexOf( rexp );
                       string = string.substring( 0, index )
                              + replacement
                              + string.substring( index + rexp.length() );
                    }
                    break;
                    
         case HEAD: if ( string.startsWith( rexp ) )
                       string = replacement + string.substring( rexp.length() );
                       
                    break;
                    
         case TAIL: if ( string.endsWith( rexp ) )
                       string = string.substring( 0, string.length() - rexp.length() )
                              + replacement;
                    break;
                    
         case BOTH: if ( string.equals( rexp ) ) 
                       string = replacement;
                    break;
      }
    
      return new JailAtom( string );
   }
}
