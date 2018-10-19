package tag.util.jail.builtin;

import java.io.*;
import java.util.*;
import tag.util.*;
import tag.util.jail.fw.*;
import tag.util.jail.*;

/**
*
*   Increments the variables in the list.<br><br>
*
*   (inc list)    // list incrementer
*   (inc)         // implicit operator "$_"<br>
*
*/
public class JF_inc extends JailFunction implements Culpable, Cloneable
{
      Librarian lib    = Jail.getContext();
      JailAtom  delta  = new JailAtom( 1 );
	   
   protected String[][] getSyntax()  { return JailFunction.NULLSYNTAX; }
   protected boolean evaluateArguments() { return true; }
   
   protected Culpable applyCommand()    
   { 
      Culpable out = null;
      
      if ( size() == 1 )
         addElement( new JailAtom( "$_" ) );
      
      for( int i=1; i<size(); i++ ) 
      {
         out = delta( getArg(i).toString() );
      }
               
      return out;
   }

   Culpable delta( String key )
   {
      JailAtom atom = (JailAtom) lib.get( key );
      
      //System.err.println( atom.d + " + " + delta.d );
      
      if ( atom != null )
         atom.init( atom.d + delta.d );
      else
         atom = delta;      
      
      //System.err.println( key + "=>" + atom.toString() );
      
      lib.put( key, atom );

      return atom;
   }
}
