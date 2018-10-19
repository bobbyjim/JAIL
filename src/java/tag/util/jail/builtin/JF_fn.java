package tag.util.jail.builtin;

import java.io.*;
import java.util.*;
import tag.util.*;
import tag.util.jail.*;
import tag.util.jail.fw.*;

/**

   fn is used to create a reference to a JailMacro:<br><br>
   
   (fn (x y z) (body))<br>

*/
public class JF_fn extends JailFunction implements Culpable, Cloneable
{
   protected String[][] getSyntax()  { return JailFunction.NULLSYNTAX; }
   protected boolean evaluateArguments() { return true; }
   
   protected Culpable applyCommand()    
   {
      JailMacro  target = new JailMacro();
      String     name   = "lambda";
      JailVector parms  = getVectorArg(1);
      JailVector body   = getVectorArg(2);
      
      target.add( name  ); // bad idea?
      target.add( parms );
      target.add( body  );
      
      return (Culpable) target;
   }
}
