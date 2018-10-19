package tag.util.jail.builtin;

import java.util.*;
import tag.util.*;
import tag.util.jail.fw.*;
import tag.util.jail.*;

/**
*
*  join()              // joins $_ with the space character<br>
*  join( $d )          // joins $_ with delimiter $d<br>
*  join( $d $foo )     // joins $foo with delimiter $d<br><br>
*
*  join( $d $foo $bar ... )  // joins $foo, $bar... with delimiter $d<br><br>
*
*  Variant<br><br>
*
*  If this function is not called by the name 'join', then<br>
*  we join on the empty string ('').<br>
*
*/
public class JF_join extends JailFunction implements Culpable, Cloneable
{
   protected String[][] getSyntax()  { return JailFunction.NULLSYNTAX; }
   protected boolean evaluateArguments() { return true; }
   
   protected Culpable applyCommand()    
   {      
   	switch( size() )
   	{
   		case  0:
   		case  1: return join( " ", 
    		                       (Culpable) Jail.getContext().get( "$_" ) );   		        
   		        
   		case  2: return join( ((Culpable) elementAt( 1 )).toString(),
   		                       (Culpable) Jail.getContext().get( "$_" ) );   		        
   		        
   		case  3: return join( ((Culpable) elementAt( 1 )).toString(),
   		                      ((Culpable) elementAt( 2 )).eval() );   	
   		                      
   		default: if ( size() > 0 )
   		            return join( ((Culpable) elementAt( 1 )).toString(), 2 );
   		         else
   		            return new JailAtom("");
   	}
   }
   
   Culpable join( String delimiter, Culpable value )
   {   	    
   	JailAtom out;
   	
   	if ( Jail.stringmaps.get( delimiter ) != null )
   	   delimiter = Jail.stringmaps.get( delimiter ).toString();
   	
   	if ( value == null )
   	{
   	   out = new JailAtom("");
   	}
   	else
   	{
      	if ( value instanceof JailAtom )
      	{
      		out = (JailAtom) value.eval();
      	}
      	else
      	{
      		String foo = "";
      		Enumeration e = ((JailVector) value).elements();
      		
      		if ( e.hasMoreElements() )
      		{
      		   foo = ((Culpable)e.nextElement()).eval().toString();
      		   
      		   while( e.hasMoreElements() )
      		      foo += delimiter + ((Culpable)e.nextElement()).eval().toString();
      		}
      		
      		out = new JailAtom( foo );
      	}
      }
      
   	   
   	return out;
   }
   
   Culpable join( String delimiter, int start )
   {
   	JailVector union = new JailVector();
   	
   	for( int i=start; i<size(); i++ )
   	   union.addElement( elementAt(i) );   	   
   	   
   	return join( delimiter, union );
   }
   
   public String toString()
   {
   	return applyCommand().toString();
   }
}
