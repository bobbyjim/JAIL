package tag.util;

import java.io.*;
import java.util.*;
import java.util.zip.*;

public class Librarian
{
      // -- singleton variable --
      
      private static Librarian instance;
      
      
      // -- member attributes --
      
      private        Hashtable library;
      private        Stack     stack;
      private        int       CRITICAL_MEMORY = 100000;
   


   // -- instance management --
   

   private Librarian() 
   {
   	library = new Hashtable( 100 );
   	stack   = new Stack();
   }
   
   public static Librarian getInstance() 
   {
      if ( instance == null )
         instance = new Librarian();
      
      return instance;
   }
   
   
   public static Hashtable getNamespace( String namespace )
   {   
      Librarian lib = Librarian.getInstance();
      
      Hashtable ns = (Hashtable) lib.get( namespace );
      if ( ns == null )
      {
         ns = new Hashtable();
         lib.put( namespace, ns );
      }
         
      return ns;
   }
   
   /*
    *  purge
    *  delete all stored data.
    *
    */
   public void purge()
   {
      SerializationAgent.purge();
   }
   

   /*
    *  getIndex
    *  return all keys to the library hashtable.
    *
    */
   public Enumeration getIndex()
   {
   	return library.keys();
   }


   /*
    *  setCriticalMemoryLevel
    *  set the point where the library dumps its contents to disk.
    *
    */
   public void setCriticalMemoryLevel( int level )
   {
      CRITICAL_MEMORY = level;
   }
   
 
   public boolean containsKey( String key )
   {
      return library.containsKey( key );
   }
   
   /*
    *  remove
    *  remove and return the labelled entry.
    *
    */
   public Object remove( String name )
   {
      return library.remove( name );
   }


   /*
    *  get
    *  return the object from memory or from disk; else null
    *
    */
   public Object get( String name ) 
   {
      Object data = library.get( name );                  // is it in memory?
      
      /*
      if ( data == null )                                 // no...
      {
         data = SerializationAgent.deserialize( name );   // then, is it on disk?
         if ( data != null )
            library.put( name, data );                    // yes; store it.
      }
      */
      
      return data;                                        // maybe, maybe null.
   }
   
   
   /*
    *  put
    *  put the object into the library.
    *  check memory levels and serialize if necessary.
    *
    */
   public void put( String name, Serializable data )
   {
      library.put( name, data );
      
      /*
      if ( Runtime.getRuntime().freeMemory() < CRITICAL_MEMORY )
      {
         Enumeration group = getIndex();
         while( group.hasMoreElements() )
         {
            String key = (String) group.nextElement();
            if ( SerializationAgent.isSerializable( key ) )
               SerializationAgent.serialize( key, (Serializable) library.remove( key ) );
         }
         //library = null; // gc
         //library = new Hashtable(100);
      }
      */
   }


   /*
    *  push(): push the current symbol table onto the stack, 
    *  and replace it with a clone for possible mutilation.
    *
    */   
   public void push()
   {
      stack.push( library );
      library = (Hashtable) library.clone();
   }
   
   /*
    *  pop(): pop the most recently saved state back into
    *  existence, annihalating the current one.
    *
    */
   public void pop()
   {
   	library = null; // gc
   	library = (Hashtable) stack.pop();
   }
      
   /*
    *  saveState
    *  save the library itself to disk.
    *
    */
   public void saveState( String archive )
   {
      SerializationAgent.serialize( archive, library );
   }
   
   
   
   /*
    *  restoreState
    *  restore the library from disk.
    *
    */
   public boolean restoreState( String archive )
   {
      Hashtable lib = (Hashtable) SerializationAgent.deserialize( archive );
      
      if ( lib != null )
      {
         library = lib;
         return true;
      }
      else
         return false;
   }
   
   
   
   /*
    *  main
    *
    *
    */
   public static void main( String[] args )
   {
      Vector table  = new Vector();
      Vector tobble = new Vector();
       
      table.addElement( "Hello there!" );
      
      Librarian lib = Librarian.getInstance();
      
      if ( lib.restoreState( "library" ) )
      {
         System.out.println( "Library restored." );
      }
      else
      {
         System.out.println( "Creating library." );
         lib.put( "FuBar", table );
         lib.saveState( "library" );       
      }
      tobble = (Vector) Librarian.getInstance().get( "FuBar" );
      
      System.out.println( "Memory:  " + table  );
      System.out.println( "Library: " + tobble );
   }
}


/**************************************************************************
*
*   SerializationAgent
*
*   This class' purpose is to encapsulate serialization methods.
*
**************************************************************************/
class SerializationAgent
{
   /*
    *  deserialize
    *  load a named object from disk; else null
    *
    */
   public static Object deserialize( String name )
   {
      Object data = null;

      try
      {
         ObjectInputStream in = new ObjectInputStream
         (
            new GZIPInputStream
            (
               new FileInputStream( name + ".ser" )                 
            )
         );
         
         data = in.readObject();
         in.close();         
         // log the event?         
      }
      catch( FileNotFoundException fnfe )
      {
      	//
      }
      catch( NotSerializableException nse ) 
      {
          nse.printStackTrace();
      }
      catch( OptionalDataException ode )
      {
         ode.printStackTrace();
      }
      catch( IOException ioe )
      {
         ioe.printStackTrace();
      }
      catch( ClassNotFoundException cnfe )
      {
         cnfe.printStackTrace();
      }
      
      return data;
   }
   
   
   public static boolean isSerializable( String name )
   {
      return Character.isLetterOrDigit( name.charAt(0) );
   }
   
   /*
    *  serialize( String, Serializable )
    *  save one element to disk.
    *
    */
   public static void serialize( String name, Serializable data )
   {
      try
      {
         ObjectOutputStream out = new ObjectOutputStream
         (
            new GZIPOutputStream
            (
               new FileOutputStream( name + ".ser" )
            )
         );
         
         out.writeObject( data );
         out.close();

         // log event?
      }
      catch( IOException ioe )
      {
         ioe.printStackTrace();
      }
   }
   
   /*
    *   purge
    *   delete all local .ser files.
    *
    */
   public static void purge()
   {
      String[] files = new File(".").list
      ( 
         new FilenameFilter()
         {
            public boolean accept( File f, String name )
            {
               return (name.endsWith( ".ser" ));
            }
         }
      );
      
      for( int i=0; i<files.length; i++ )
      {
         //System.err.println( files[i] );
         new File( files[i] ).delete();
      }
   }
}
