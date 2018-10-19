package tag.util.jail;

import java.io.*;
import java.util.*;
import tag.util.jail.fw.*;

/*********************************************************************************
*
*   Defines the function class for JAIL.
*
*
*********************************************************************************/
public abstract class JailFunction extends JailVector implements Culpable, Cloneable
{
      String name;
      public static String[][] NULLSYNTAX = new String[][] {{}};
      
	public Culpable eval()
	{
      name = firstElement().toString();
		if ( analyzeArguments() == false )
		{
		   System.err.println( name + ": argument list incorrect." );
		   return new JailAtom( "argument list ERROR" );
		}
		if ( evaluateArguments() == false )
		{
		   System.err.println( name + ": argument value incorrect." );
		   return new JailAtom( "argument value ERROR" );
		}
		return applyCommand();
	}

   public String getName()
   {
      return name;
   }
   
   protected boolean minimalArgumentCheck( int num )
   {
      if ( size() < num )
      {
         System.err.println( "ERROR: " + getName() + " has " + num  + " arguments, but requires " + num );
         return false;
      }
      return true;
   }
   
   protected boolean absoluteArgumentCheck( int num )
   {
      if ( size() != num )
      {
         System.err.println( "ERROR: " + getName() + " has " + num  + " arguments, but requires " + num );
         return false;
      }
      return true;
   }
   
   private String getType( String classname )
   {
      if ( classname.equals( "tag.util.jail.JailAtom"     ) ) return "atom";
      if ( classname.equals( "tag.util.jail.JailVector"   ) ) return "list";
      if ( classname.equals( "tag.util.jail.JailHash"     ) ) return "hash";
      return classname;
   }
   
   protected boolean analyzeArguments( String[] classes )
   {
      boolean result = true;
      for( int i=0; i<classes.length; i++ )
      {
         String o = getArg(i+1).getClass().getName();
         if ( o.equals(classes[i]) == false )
         {
            String type = getType( classes[i] );
            System.err.println( "eval() error: argument " + (i+1) + " must be of type " + type );
            result = false;
         }
      }
      return result;
   }

   protected boolean analyzeArguments()
   {
      boolean out = true;
      
      String[][] syntax = getSyntax();
      
      for( int i=0; i<syntax.length; i++ )
         if ( size() == syntax[i].length + 1 )
            out = analyzeArguments( syntax[i] );

      return out; 
   }

   abstract protected String[][] getSyntax();   
   abstract protected boolean evaluateArguments();
   
   protected abstract Culpable applyCommand();
   
	public String toString()
	{
		return "f:" + super.toString();
	}
	
	public String explain()
	{
	   return "(function: " + super.explain() + ")";
	}
}
