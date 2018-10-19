package tag.util.jail.builtin;

import java.io.*;
import java.util.*;
import tag.util.*;
import tag.util.jail.*;
import tag.util.jail.fw.*;

/**

   Define is used to define user data.<br><br>
   
   (define)                    - returns a vector of Jail function names<br>
   (define foo)                - sets foo to 1<br>
   (define foo bar)            - sets foo to bar<br>
   (define foo (x y z) (body)) - defines a function<br><br>
   
   
   If you specifically want to create a particular type,<br>
   use these functions:<br><br>
   
   (atom   foo value)<br>
   (vector foo (a b c))<br>
   (hash   foo (k1 v1) (k2 v2) (k3 v3))<br>

*/
public class JF_define extends JailFunction implements Culpable, Cloneable
{
   protected String[][] getSyntax()  { return JailFunction.NULLSYNTAX; }
   protected boolean evaluateArguments() { return true; }
   
   protected Culpable applyCommand()    
   {
      Culpable target = null;
      
      //
      //  Inventory: (define)
      //
      if ( size() <= 1 )
      {
         target = new JailVector();
         ((JailVector)target).addMembers( Jail.getInstance().getRegistry() );
      }
      else
      //
      //  A Flag: (define foo)
      //  Turn it into an assignment.
      //
      if ( size() == 2 )
      {
         target = new JailAtom( "1" ); 
         Jail.getContext().put( ( String) elementAt(1).toString(), target );
      }
      else
      //
      //   Assignment/Declaration (define foo bar)
      //
      if ( size() == 3 )
      {
         target = ((Culpable) elementAt(2)).eval();    
   	   Jail.getContext().put( (String) elementAt(1).toString(), target );
   	}
   	else
   	//
   	//   A function
   	//
   	{
   	   String     key   = (String)     elementAt(1).toString();
   	   JailVector parms = (JailVector) elementAt(2);
   	   JailVector body  = (JailVector) elementAt(3);

     		target = new JailMacro();
     		((JailMacro)target).add( key   );
     		((JailMacro)target).add( parms );
     		((JailMacro)target).add( body  );
         
         Jail.getContext().put( key, target );
      }

   	return target;
   }
}
