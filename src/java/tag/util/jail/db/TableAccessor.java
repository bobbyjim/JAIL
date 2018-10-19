package tag.util.jail.db;

import java.io.*;
import java.util.*;

/*********************************************************************
*
*  TableAccessor
*
*********************************************************************/
public class TableAccessor
{   
   ///////////////////////////////////////////////////////////////////
   //
   //  SELECT fields FROM table.
   //
   ///////////////////////////////////////////////////////////////////
   public static Vector selectField( Vector tab, int col )
   {
      Vector out = new Vector( tab.size() );
      Enumeration elems = tab.elements();
      
      while( elems.hasMoreElements() )
      {
         Vector row   = (Vector) elems.nextElement();
         out.addElement( row.elementAt( col ) );
      }
      return out;
   }
   
   ///////////////////////////////////////////////////////////////////
   //
   //  SELECT one-field FROM table AT row
   //
   ///////////////////////////////////////////////////////////////////
   public static String selectField( Vector tab, int row, int col )
   {
      Vector r = (Vector) tab.elementAt( row );
      return (String) r.elementAt( col );
   }
   
   
   ///////////////////////////////////////////////////////////////////
   //
   //  SELECT rows FROM table WHERE field EQUALS tag
   //
   ///////////////////////////////////////////////////////////////////
   public static Vector selectRows( Vector tab, int col, String tag )
   {
      return selectRows( tab, new int[] { col }, tag );
   }
   
   public static Vector selectRow( Vector tab, int row )
   {
      return (Vector) tab.elementAt( row );
   }
   
   ///////////////////////////////////////////////////////////////////
   //
   //  SELECT rows FROM table WHERE column_numbers_catenated EQUALS key
   //
   ///////////////////////////////////////////////////////////////////
   public static Vector selectRows( Vector tab, int[] col, String key )
   {
      Vector out = new Vector( tab.size() );
      Enumeration elems = tab.elements();
      
      while( elems.hasMoreElements() )
      {
         Vector row = (Vector) elems.nextElement();
         String keyField = "";

         for( int i=0; i<col.length; i++ )
            keyField += row.elementAt( col[i] );
            
         if (  key == null  ||  keyField.equals( key )  )
            out.addElement( row );
      }
      return out;
   }
   
   public static Vector selectRows( Vector tab, Vector col, String key )
   {
      int[] columns = TableAccessor.makeArray( col );
         
      return TableAccessor.selectRows( tab, columns, key );
   }
   
   /////////////////////////////////////////////////////////////////////
   //
   //   SELECT rows FROM table WHERE table.col EQUALS table2.col
   //
   /////////////////////////////////////////////////////////////////////
   public static Vector selectRows( Vector tab1, int col1, int col2, Vector tab2 )
   {
      Vector      out  = new Vector( tab1.size() );
      
      for( int i=0; i<tab1.size(); i++ )
      {
         Vector ar = (Vector) tab1.elementAt( i );
         String a  = (String) ar.elementAt( col1 );

         for( int j=0; j<tab2.size(); j++ )
         {
            Vector br = (Vector) tab2.elementAt( j );
            String b  = (String) br.elementAt( col2 );
            
            if ( a.equals( b ) )
            {
               Vector row = new Vector(1);
               
               for( int m=0; m<ar.size(); m++ )
                  row.addElement( ar.elementAt( m ) );
                  
               for( int n=0; n<br.size(); n++ )
                  row.addElement( br.elementAt( n ) );

               out.addElement( row );
               continue;
            }
         }
      }
      
      return out;
   }

/*
   public static Vector selectRows( Vector tab1, Vector col1, Vector tab2, Vector col2 )
   {
      int[] c1 = makeArray( col1 );
      int[] c2 = makeArray( col2 );
      
      return TableAccessor.selectRows( tab1, c1, tab2, c2 );
   }
*/   
   
   static int[] makeArray( Vector source )
   {
      int[] columns = new int[ source.size() ];
      int i;
      Object element = null;
      try
      {
         for( i=0; i<columns.length; i++ )
         {
            element = source.elementAt(i);
            columns[i] = Integer.parseInt( (String) element );
         }
      }
      catch( NumberFormatException nfe )
      {
         System.err.println( "SELECT: non-numeric argument detected (" + element + ")" );
         columns = null;
      }
         
      return columns;
   }
} 