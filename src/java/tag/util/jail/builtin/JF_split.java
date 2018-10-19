package tag.util.jail.builtin;

import java.util.*;
import tag.util.*;
import tag.util.jail.fw.*;
import tag.util.jail.*;

/**
*
*  This function tokenizes a string into a vector, based on a delimiter:<br><br>
*
*  (split)          -  splits $_ on the space character.<br>
*  (split d)        -  splits $_ on the delimiter d.<br>
*  (split d string) -  splits string on delimiter d.<br>
*
*/
public class JF_split extends JailFunction implements Culpable, Cloneable
{
   protected String[][] getSyntax()  { return JailFunction.NULLSYNTAX; }
   protected boolean evaluateArguments() { return true; }
   
   protected Culpable applyCommand()    
   {
   	JailVector out = new JailVector();
   	
   	String index = "$_";
   	String token = " ";
   	
   	switch( size() )
   	{
   		case  0:
   		case  1: // split()
   		         token = " ";
   		         index = "$_";
   		         break;
   		        
   		        
   		case  2: // split( ' ' )
    		         token = ((Culpable) elementAt( 1 )).toString();
   		         index = "$_";
   		         break;
   		        
   		default: // split( ' ', $foo )
   		         token = ((Culpable) elementAt( 1 )).toString();
   		         index = ((Culpable) elementAt( 2 )).toString();
   		         break;
   	}
   	
   	Culpable value = (Culpable) Jail.getContext().get( index );
   	
   	String s = index;

      if ( value != null ) 
         s = value.toString();
         
      return split( token, s );         	
   }
   
   Culpable split( String token, String string )
   {
      JailVector out = new JailVector();
      StringTokenizer tok = new StringTokenizer( string, token );
      
      while( tok.hasMoreTokens() )
         out.addElement( new JailAtom( tok.nextToken() ) );
         
      return out;
   }
   
   public String toString()
   {
   	return applyCommand().toString();
   }
}
