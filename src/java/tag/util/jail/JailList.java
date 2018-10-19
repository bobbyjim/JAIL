package tag.util.jail;

import java.io.*;
import java.util.*;
import tag.util.jail.fw.*;
import tag.util.*;

/************************************************************

   A JailList represents aggregate data.
      
************************************************************/
abstract public class JailList extends Vector implements Culpable, Cloneable
{
   //////////////////////////////////////////
   //
   //
	abstract public Culpable eval();
	//
	//
   //////////////////////////////////////////
	
	public Object clone()
	{
		return super.clone();
	}

   /*******************************************************************
   *
   *   The easy, safer ways to get at your argument list.
   *
   *******************************************************************/
   public Culpable getArg( int index )
                         {
                            Culpable out = null;
                            
                            if ( size() < index )
                            {
                               System.err.println( "Error trying to index beyond array size.\n" );
                            }
                            else
                               out = (Culpable) elementAt(index);
                               
                            return out;
                         }

   public String     getStringArg( int index ) { return getArg(index).eval().toString();   }   
   public JailVector getVectorArg( int index ) { return (JailVector) getArg(index).eval(); }
   public JailAtom   getAtomArg(   int index ) { return (JailAtom) getArg( index ).eval(); }
   public int        getIntArg(    int index ) { return (int)( getAtomArg(index) ).d;      }   
   public JailHash   getHashArg(   int index ) 
   { 
      Culpable out = getArg( index ).eval();
      if ( out instanceof JailHash )
         return (JailHash) out;
      else
      {
         if ( Jail.getInstance().getDebug() )
            System.err.println( "JailList:getHashArg(): element is " + out.getClass().getName() );
         return null;
      }
   }

   /*****************************************************************
   *
   *   Add members to our List.
   *
   *****************************************************************/
   public void addMembers( Enumeration e )
   {
		while( e.hasMoreElements() )
		   addElement( e.nextElement() );
	}
   
	public void addMembers( JailList v )
	{
		addMembers( v.elements() );
	}
   
   public void addMembers( JailAtom a )
   {
      addElement( a );
   }
   
   public void addMembers( JailHash h )
   {
      addMembers( h.elements() );
   }
   
   /*****************************************************************
   *
   *   Print our List.
   *
   *****************************************************************/
   public String toString( String delimiter )
   {
      String out = "";
      Enumeration e = elements();
            
      if ( e.hasMoreElements() )
         out = translate( e.nextElement().toString() );
         
      while( e.hasMoreElements() )
         out += delimiter + translate( e.nextElement().toString() );
      
      return out;
   }
   
   private String translate( String elem )
   {
      if ( elem.equals( "\n" ) ) return "\\n";
      else 
      if ( elem.equals( "\t" ) ) return "\\t";
      else
         return elem;
   }
   
	public String toString()
	{
	   return "(" + toString( " " ) + ")";
	}
	
	public String explain()
	{	   
	   String out = "";
	   
	   for( int i=0; i<size(); i++ )
	      out += ((Culpable) elementAt(i)).explain() + " ";
	   
	   return out;	   
	}
}
