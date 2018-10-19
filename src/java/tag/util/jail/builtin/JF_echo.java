package tag.util.jail.builtin;

import java.util.*;
import tag.util.*;
import tag.util.jail.fw.*;
import tag.util.jail.*;

/**
*
*  This function prints its arguments to STDOUT.
*
*/
public class JF_echo extends JailFunction implements Culpable, Cloneable
{
   protected String[][] getSyntax()  { return JailFunction.NULLSYNTAX; }
   protected boolean evaluateArguments() { return true; }
   
   protected Culpable applyCommand()    
   {
      Culpable out;
//      JailVector out = new JailVector();
      
   	if ( size() == 1 )
   	{
   	   String s = ((Culpable) Jail.getContext().get( "$_" )).toString();
   	   System.out.print( s );
   	   out = (Culpable) Jail.getContext().get( "$_" );
//   	   out.addElement( new JailAtom( s ) );
   	}
   	else
   	{
   	   out = getArg(1);
   	   for( int i=1; i<size(); i++ )
   	   {
   	      String s = getStringArg(i);
   	      System.out.print( s );
//   	      out.addElement( new JailAtom( s ) );
         }
   	}
   
   	//
   	//  Return the 1st element.
   	//
   	return out;
   }
}
