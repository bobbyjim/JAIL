package tag.util.jail.builtin;

import java.io.*;
import java.util.*;
import tag.util.*;
import tag.util.jail.fw.*;
import tag.util.jail.*;

/**
*
*   Returns the provided value + 1.<br><br>
*
*   (1+ fubar)    // returns fubar + 1 <br>
*   (1+)          // returns $_ + 1    <br>
*
*/
public class JF_oneplus extends JailFunction implements Culpable, Cloneable
{	   
      int delta = 1;
      
   protected String[][] getSyntax()  { return JailFunction.NULLSYNTAX; }
   protected boolean evaluateArguments() { return true; }
   
   protected Culpable applyCommand()    
   {      
      if ( size() == 1 )
         addElement( new JailAtom( "$_" ) );
      
      JailAtom val = (JailAtom) getArg(1).eval();
      
      return new JailAtom( val.d + delta );
   }
}
