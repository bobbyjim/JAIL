package tag.util.jail.builtin;

import java.io.*;
import java.util.*;
import tag.util.*;
import tag.util.jail.*;
import tag.util.jail.fw.*;

/**

   Undef removes an identifier from its namespace.<br><br>
   
   (undef foo)<br><br>
   
   It returns the removed datum.
*/
public class JF_undef extends JailFunction implements Culpable, Cloneable
{
   protected String[][] getSyntax()  { return JailFunction.NULLSYNTAX; }
   protected boolean evaluateArguments() { return true; }
   
   protected Culpable applyCommand()    
   {
      String key = elementAt(1).toString();
      return (Culpable) Jail.getContext().remove( key );
   }
}
