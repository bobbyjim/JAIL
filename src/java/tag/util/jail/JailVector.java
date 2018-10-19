package tag.util.jail;

import java.io.*;
import java.util.*;
import tag.util.jail.fw.*;
import tag.util.*;

/************************************************************

   A JailVector represents two types of user data:
   
      (1) a literal list of data
      (2) access into the data of a property object
      
   The two are distinguished by the fact that a property object
   has an entry in the symbol table (it has been defined), 
   while literal data does not.
   
************************************************************/
public class JailVector extends JailList implements Culpable, Cloneable
{
   public JailVector()
   {
      super();
   }
   
   public JailVector( JailAtom a )
   {
      super();
      addElement( a );
   }
   
   public JailVector( JailVector a )
   {
      super();
      addMembers( a );
   }
   
   public JailVector( Enumeration e )
   {
      super();
      addMembers( e );
   }
	
	public Culpable parse()
	{
	   return null;
	}
	
	public Culpable eval()
	{	
	   if ( size() == 0 ) return this;
	   
	   Culpable obj = (Culpable) Jail.getContext().get( firstElement().toString() );

      if ( obj != null )
      {
         if ( obj instanceof JailMacro )
         {
            //
            //  I am a macro
            //
            JailMacro macro = (JailMacro) ((JailVector)obj).clone();
            macro.addMembers( this );
            return macro.eval();
         }
         else
         if ( obj instanceof JailFunction )
         {
            //
            //  I am a registered function.
            //
            JailFunction func = (JailFunction) ((JailVector)obj).clone();
            func.addMembers( this );
            return func.eval();
         }
         else          
         //
         //           This test used to be size() > 1, but I didn't
         //           document why.  I'm sure this will bite me later,
         //           but I need to treat lists as lists, even if
         //           they're of size 1.
         //
         if ( obj instanceof JailVector && ((JailVector)obj).size() > 0)
         {
            //
            //  I am an index into a JailVector
            //
            JailVector in  = (JailVector) obj;
            
            if ( size() == 2 ) try
            {
               return (Culpable) ((Culpable) in.elementAt( getIntArg(1) )).eval(); 
            }
            catch( Exception e )
            {
               // not numeric!  no problem, return the cdr of the vector
               JailVector out = (JailVector) in.clone();
               out.removeElementAt(0);
               return out;
            }
            

            JailVector out = new JailVector();
            
            for( int i=1; i<size(); i++ )
            {
               out.addElement( in.elementAt( getIntArg( i ) ) );
            }
            return out;
         }
         else if ( obj instanceof JailHash )
         {
            //
            //  I am an index into a JailHash, or perhaps just a JailHash.
            //
            JailHash   in  = (JailHash) obj;
            
            if ( size() == 1 )
               return in;        // Just a Hash
               
            if ( size() == 2 )
            {
               if ( Jail.getInstance().getDebug() )
               {
                  System.err.println( "fetching >" + getStringArg(1) + "<" );
               
                  Enumeration keys = in.keys();
                  while( keys.hasMoreElements() )
                  {
                     String key = (String) keys.nextElement();
                     System.err.println( key + ":" + in.get( key ) );
                  }
                  System.err.println( in.get( getStringArg(1) ) );
               }
               return (Culpable) ((Culpable) in.get( getStringArg(1) )).eval();
            }
                  
            JailVector out = new JailVector();

            for( int i=1; i<size(); i++ )
            {
               out.addElement( in.get( getStringArg(i) ) );
            }
            return out;
         }
         else 
         {
            {
               System.err.println( "<<info: " + firstElement() + " is data, not a function.>>" );
               //
               //  I am nothing special.  Simply evaluate my members.
               //
               JailVector out = new JailVector();
               Enumeration elems = elements();
               
               while( elems.hasMoreElements() )
               {
                  out.addElement( ((Culpable) elems.nextElement()).eval() );
               }
               return out;
            }
         }
      }
		else
   		return this;
	}
	
	public String explain()
	{
	   return "(Vector: " + super.explain() + ")";
	}
}
