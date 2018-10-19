package tag.util.jail;

import java.io.*;
import java.util.*;
import tag.util.*;
import tag.util.jail.fw.*;

/***********************************************************

   Jail Interpreter
   
***********************************************************/
public class JailInterpreter
{
   public Culpable interpret( JailVector in )
   {
   	JailVector out = new JailVector();
   	
   	Enumeration e = in.elements();

   	while( e.hasMoreElements() )
   	{
   		Culpable v = (Culpable) e.nextElement();
   		
   		try
   		{
   		   out.addElement( v.eval() );
   		}
   		catch( Exception ex )
   		{
   		   ex.printStackTrace();
   		}   		
   	}

   	return out;
   }
   
   public static void main( String[] args ) throws Exception
   {
   	for( int i=0; i<args.length; i++ )
   	   System.err.println
   	   ( 
   	      (new JailInterpreter()).interpret
   	      ( 
   	         (new JailParser( new FileReader( args[i] ) )).parse()
   	      )
   	   );
   }
}
