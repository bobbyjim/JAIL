package tag.util.jail;

import java.io.*;
import java.util.*;
import tag.util.jail.fw.*;

/***********************************************************************
*
*   JailHash
*
***********************************************************************/
public class JailHash extends Hashtable implements Culpable, Cloneable
{
   public JailHash()
   {
      super();
   }
   
   public JailHash( JailVector v )
   {
      super();
      for( int i=0; i<v.size(); i++ )
      {
         Vector pair = (Vector) v.elementAt(i);
         String key  = pair.elementAt(0).toString();
         Object val  = pair.elementAt(1);
         
         put( key, val );
      }
   }
      
   /**************************************************
   *
   *   JailFunctions have a function call first, then 
   *   the data follows.  Therefore, we start parsing
   *   pairs at index 1.
   *
   **************************************************/
   public JailHash( JailFunction fn )
   {
      super();
      for( int i=1; i<fn.size(); i++ )
      {
         if ( fn.elementAt(i) instanceof Vector )
         {
            Vector pair = (Vector) fn.elementAt(i);
            String key  = pair.elementAt(0).toString();
            Object val  = pair.elementAt(1);
         
            put( key, val );
         }
      }
   }
   
   public JailHash( JailHash h )
   {
      super();
      Enumeration mykeys = h.keys();
      while( mykeys.hasMoreElements() )
      {
         Object key = mykeys.nextElement();
         put( key, h.get( key ) );
      }
   }
   
   public JailHash( Hashtable h )
   {
      super();
      Enumeration mykeys = h.keys();
      while( mykeys.hasMoreElements() )
      {
         Object key = mykeys.nextElement();
         put( key, h.get( key ) );
      }
   }
   
   public Object clone()
   {
      return new JailHash( this );
   }

	public Culpable parse()
	{
	   return null;
	}
	
	
   public Culpable eval()
   {
      Enumeration mykeys = keys();
      
      while( mykeys.hasMoreElements() )
      {
         Object key = mykeys.nextElement();
         put( key, ((Culpable) get( key )).eval() );  // NEW
      }
      
   	return this;
   }
   
   public void put( JailVector pair )
   {
      put( pair.elementAt(0).toString(), pair.elementAt(1) );
   }
   
   //
   //   This toString() method just prints keys!
   //
   public String toString( String delimiter )
   {
      String      out  = "";
      Enumeration mykeys = keys();
      
      if ( mykeys.hasMoreElements() )
         out += "" + mykeys.nextElement();
         
      while( mykeys.hasMoreElements() )
         out += delimiter + mykeys.nextElement();
         
      return out;
   }
   
   //
   //   This toString() method prints both keys and values!
   //
   public String toString()
   {
      Enumeration mykeys = keys();
      String out = "(hash ";
   
      while( mykeys.hasMoreElements() )
      {
         Object k = mykeys.nextElement();
         out += "(" + k.toString() + " " + get(k).toString() + ")";
      }
      
      return out + ")";
   }
   
   public String explain()
   {
      Enumeration mykeys = keys();
      String out = "(hash ";
      
      while( mykeys.hasMoreElements() )
      {
         Object k = mykeys.nextElement();
         out += "(" + k.toString() + " " 
             +  ((Culpable)get(k)).explain() + ")";
      }
      
      return out + ")";
   }
}
