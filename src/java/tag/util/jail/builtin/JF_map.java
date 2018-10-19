package tag.util.jail.builtin;

import java.io.*;
import java.util.*;
import tag.util.*;
import tag.util.jail.fw.*;
import tag.util.jail.*;

/**
*
*   The map loop takes the form:<br><br>
*
*   (map function list)<br><br>
*
*   It is essentially an implicit foreach loop with the list and body<br>
*   reversed in position.<br><br>
*
*   The pronoun variable is $_ (of course).<br>
*
*/
public class JF_map extends JailFunction implements Culpable, Cloneable
{
	   String index;
	   
   protected String[][] getSyntax()  { return JailFunction.NULLSYNTAX; }
   protected boolean evaluateArguments() { return true; }
   
   protected Culpable applyCommand()    
   {
      Librarian lib = Jail.getContext();
      
      if ( size() != 3 )
         return null;
      
      String pronoun = "$_";
      
      Culpable   function = getArg(1);
      JailVector list     = getVectorArg(2);
      
      // the applied element list is all remaining parameters.
            
      JailVector out = new JailVector();
      
      /********************************************************************
      *
      *   foreach element in list
      *      store that element into the pronoun
      *      clone the function code and execute
      *      store the result
      *
      ********************************************************************/
      for( int i=0; i<list.size(); i++ )
      {
         lib.put( pronoun, ((Culpable) list.elementAt(i)).eval() );
         Culpable code = (Culpable) function.clone();
         out.addElement( code.eval() );
      }
      
      return out;
   }
}
