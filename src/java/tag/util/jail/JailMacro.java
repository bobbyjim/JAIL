package tag.util.jail;

import java.io.*;
import java.util.*;
import tag.util.*;
import tag.util.jail.fw.*;
/*************************************************************************
*
*   JailMacro
*
*   A macro is a user-defined function of the form:
*
*   (define macro-name params body)
*
*************************************************************************/
public class JailMacro extends JailFunction implements Culpable, Cloneable
{
   //  definition data:
    int MACRO   = 0;   // String
    int FORMALS = 1;   // JailVector
    int BODY    = 2;   // JailVector
   
   //  run-time data:
    int CALLED  = 3;   // JailAtom (same as MACRO)
    // parameters are the remaining atoms
    
   //
   //   make sure we have enough args
   //
   protected String[][] getSyntax()  { return JailFunction.NULLSYNTAX; }

   //
   //   assign the parameters into their respective slots.
   //   NB: it might be a good idea to store temporary parameters
   //   into special variables (e.g. $foo instead of foo) to give
   //   them their own namespace... i dunno.
   //
   protected boolean evaluateArguments() 
   {
      Librarian  lib   = Jail.getContext();
   	JailVector names = (JailVector) elementAt( FORMALS );      
      lib.push();
      
      //
      //  I don't think I like this at all...
      //   	
   	for( int i=0; i<names.size(); i++ )
   	{
   	   String id = names.elementAt(i).toString();
   	   Culpable param;

   	   if ( size() > i+4 )
   	   {
   	      param = ((Culpable) elementAt(i+4)).eval(); // eval?
   	   }
   	   else
   	   {
   	      System.err.println( "ERROR: not enough parameters for " + elementAt( MACRO ) + names );
   	      param = new JailAtom(0);
   	   }
   	   
   	   lib.put( id, param );
   	}
   	return true;
   }

   //
   //  Here's the outside way to do it: inject this list of real arguments
   //  for the formal parameters.
   //
   public void injectArguments( JailVector args )
   {
      addElement( "macro" );
      for( int i=0; i<args.size(); i++ )
         addElement( args.elementAt(i) );
   }
   
   void unevaluateArguments()
   {
   	Jail.getContext().pop();
   }  
    	
   //
   //   interpret the Jail code
   //
   protected Culpable applyCommand()    
   {
      Culpable body = (Culpable)(((Culpable)elementAt( BODY )).clone());
      
      //System.err.println( "body=" + body );
      
      Culpable out  = body.eval();
            
   	unevaluateArguments();
   	
   	return out;
   }
   
	public String toString()
	{
		return "(fn " 
		     + getArg( FORMALS ).toString()
		     + getArg( BODY    ).toString()
		     + ")";
	}
	
	public String explain()
	{
	   return "(macro "
	        + getStringArg( 0 ) + ": "
	        + getArg( FORMALS ).explain() + " "
	        + getArg( BODY    ).explain()
	        + ")";
	}
	
}
