package tag.util.jail.builtin;

import java.io.*;
import java.util.*;
import tag.util.*;
import tag.util.jail.fw.*;
import tag.util.jail.*;

/**
*
*   The foreach loop takes two forms:<br><br>
*
*   (foreach counter list body)  // explicit iterator<br>
* 
*   (foreach list body)          // implicit iterator "$_"<br>
*
*/
public class JF_foreach extends JailFunction implements Culpable, Cloneable
{
	   String index;
      Librarian lib = Jail.getContext();
	   String[][] syntax = new String[][]
	   {{}};
	   /*
	      "tag.util.jail.JailAtom",
	      "tag.util.jail.JailVector",
	      "tag.util.jail.JailVector"
	    },
	    {
	      "tag.util.jail.JailVector",
	      "tag.util.jail.JailVector"
	    }
	   };
	   */
	
	protected String[][] getSyntax()
	{
	   return syntax;
	}
   
   protected boolean evaluateArguments() { return true; }
   
   //
   //   Get the "pronoun" iterator
   //
   private String getPronoun()
   {
      if ( size() == 3 ) return "$_";
      //
      //   We want the variable name, NOT the value which may be
      //   contained in the variable.
      //
      else return getArg(1).toString();
   }
   
   //
   //   Get the list to iterate over.
   //
   private JailVector getList()
   {
      return (JailVector) ((Culpable)getArg( size() - 2 ).clone()).eval();
   }
   
   //
   //   Get the function body to execute on each iteration.
   //
   private Culpable getBody()
   {
      return getArg( size() - 1 );
   }
   
   protected Culpable applyCommand()    
   {      
      String     pronoun = getPronoun();
      JailVector list    = getList();
      Culpable   body    = getBody();
      
      //System.err.println( "foreach: " + list );
      
      JailVector out;
        
      try
      {
         if ( (list.size() == 3) && list.elementAt(1).toString().equals( "to" ) )
            out = rangeIterate( pronoun, 
                                Integer.parseInt( list.elementAt(0).toString() ), 
                                Integer.parseInt( list.elementAt(2).toString() ),
                                body );
         else
            out = listIterate( pronoun, list, body );
      }
      catch( Exception e )
      {
         out = listIterate( pronoun, list, body );
      }
         
      return out;
   }
   
   private JailVector listIterate( String iterator, 
                                   JailVector list, 
                                   Culpable body )
   {
      JailVector out = new JailVector();
      
      for( int i=0; i<list.size(); i++ )
      {
         Culpable elem = (Culpable) list.elementAt(i);
         lib.put( iterator, elem );
         // System.err.println( "foreach: " + iterator + " = " + elem );
         
         Culpable code = (Culpable) body.clone();
         out.addElement( code.eval() );
      }      
      
      return out;
   }   
   
   private JailVector rangeIterate( String iterator, 
                                    int    begin, 
                                    int    end,
                                    Culpable body )
   {
      JailVector out = new JailVector();
            
      for( int i=begin; i<=end; i++ )
      {
         lib.put( iterator, new JailAtom( i ) );
         Culpable code = (Culpable) body.clone();         
         out.addElement( code.eval() );
      }
      
      return out;
   }
   
}
