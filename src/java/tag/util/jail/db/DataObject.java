package tag.util.jail.db;
/***************************************************************************
*
*   The generic Data Object
*
*   This superclass holds field data in an array of Objects,
*   key data in an Object,
*   and has a getField() method and other general access methods.
*
*   To Use:
*
*   Write a subclass and override buildKey().
*   That's it!
*
*   If you have special logic or verification to do, then
*   also override build().
*
***************************************************************************/
import java.io.*;
import java.util.*;

public class DataObject implements Serializable
{
             Object[] fields  = null;
             Object   key     = null;
      //static String   delim   = " "; // "\u00b0";
   
   public void build( Object[] fields )
   {
      this.fields = new Object[ fields.length ];
      
      for( int i=0; i<fields.length; i++ )
         this.fields[i] = fields[i]; // .clone();
      
      buildKey();
   }
   
   public void build( Vector row )
   {
      fields = new Object[ row.size() ];
      Enumeration elems = row.elements();
      int i= 0;
      
      while( elems.hasMoreElements() )
         fields[i++] = elems.nextElement();
               
      buildKey();
   }
   
   
   public void buildKey()
   {
      key = fields[0];
   }
   
   
   public Object getKey()
   { 
      return key;
   }
   
   
   public Object getField( int i )
   {
      if ( i > 0 && i < fields.length )
         return _getField( i );
      else
         return null;
   }
   
   
   private Object _getField( int i )
   {
      return fields[i];
   }
   
   
   public void updateField( Object value, int i )
   {
      if ( i > 0 && i < fields.length )
         fields[i] = value;
   }
   
   
   public Object[] getFields( int[] f )
   {
      Object[] response = new Object[ f.length ];
      
      for( int i=0; i<f.length; i++ )
         response[i] = getField( f[i] );
         
      return response;
   }
   
   
   public String getName()
   {
      return getClass().getName();
   }
   
   
   public String getData()
   {
      String out = " (";
      
      for( int i=0; i<fields.length; i++ )
         out += " \"" + fields[i] + "\""; // + delim;
         
      return out + " ) ";
   }
   
   
   public String toString()
   {
      return "(put " + getName() + getData() + ")";
   }
   
   
   public static void main( String[] args )
   {
      String[] fields = new String[]
      {
         "a", "b", "c", "d"
      };
      
      DataObject test = new DataObject();
      test.build( fields );
      
      System.out.println( "class: " + test.getName()  );
      System.out.println( "key  : " + test.getKey()   );
      System.out.println( "data : " + test.getData()  );
      System.out.println( "struc: " + test.toString() );
   }
}