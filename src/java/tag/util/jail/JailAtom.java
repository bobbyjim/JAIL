package tag.util.jail;

import java.util.*;
import tag.util.jail.fw.*;
import tag.util.*;

public class JailAtom implements Culpable
{
	   public String s;
	   public double d;
	   public boolean numeric;
	   
	public JailAtom( String atom ) 
	{
	   init( atom );
	}
	   
   public void init( String atom )
	{	
	   s    = atom;	   
	   d    = 0.0;
	   
	   try
	   {
	      d = new Double( atom ).doubleValue();
	   }
	   catch( Exception e ) {}
	   
	   numeric = false;
	}
	
	public JailAtom( double atom ) 
	{
	   init( atom );
	}
	
	public void init( double atom )
	{
	   s = "" + atom;	
	   d = atom;
	   numeric = true;
	   
	   if ( d == (int)d )
	   {
	      s = "" + (int)atom;
	   }
	}
	
	public JailAtom( int atom )
	{
	   init( (long) atom );
	}
	
	public JailAtom( long atom ) 
	{
	   init( atom );
	}
	
	public void init( long atom )
	{	
	   s = "" + atom;	
	   d = atom * 1.0;
	   numeric = true;
	}
	
	public JailAtom( JailAtom ja )
	{
	   init( ja );
	}
	
	public void init( JailAtom ja )
	{
	   this.s = ja.s;
	   this.numeric = ja.numeric;
	   this.d = ja.d;
	}
	
	
	public Culpable elementAt( int pos )
	{
	   if ( numeric )
	      s = "" + d;
	      
	   if ( s.length() <= pos )
	      return new JailAtom( s.substring( pos, 1 ) );
	   else
	      return null;
	}
	
	public Object clone()
	{
	   return new JailAtom( this );
	}
	
	public Culpable parse()
	{
	   return null;
	}
	
   public Culpable eval()
   {
   	//
   	//  an atom evaluates to:
   	//  (1) a Librarian value, 
   	//  (2) a Jail string mapping, 
   	//  (3) or itself.
   	//
   	Culpable  val = this;
      String    key = val.toString();
      
      //
      //   Is it in the dictionary?
      //
      if ( Jail.getContext().containsKey( key ) )
         val = (Culpable) Jail.getContext().get( key );
      else
      //
      //   Is it a stringmap?
      //
      if ( Jail.stringmaps.containsKey( key ) )
         val = (Culpable) Jail.stringmaps.get( key );
      else
      //
      //   None of the above: it's itself
      //
         val = this;         
      
      return val;
   }
   
   public String toString( String delimiter )
   {
      return s;
   }
   
   public String toString() 
   {
   	return s;
   }
   
   public String explain()
   {
      return "Atom: " + toString();
   }
}
