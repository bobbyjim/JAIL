package tag.util.jail;

import java.io.*;
import java.util.*;
import tag.util.*;
import tag.util.jail.fw.*;

public class JailParser extends StreamTokenizer
{
      LineNumberReader rdr;
   
   public JailParser( Reader fr )
   {
      this( new LineNumberReader( fr ) );
   }
   
   public JailParser( LineNumberReader fr )
   {
      super( fr );
      rdr = fr;
      
      eolIsSignificant( false );
      quoteChar( '\'' );
      quoteChar( '"' );

      //
      //  wordChars() represents chars that are to be treated as
      //  parts of words
      //
      wordChars( '$',  '$' );
      wordChars( '%',  '%' );
      wordChars( '@',  '@' );
      wordChars( '?',  '?' );      
      wordChars( '!',  '!' );
      wordChars( '-',  '-' );
      wordChars( '_',  '_' );
      wordChars( '=',  '=' );
      wordChars( '\\', '\\' );
      wordChars( '<',  '<' );
      wordChars( '>',  '>' );
      
      //
      //  ordinaryChars() are chars that are never part of tokens
      //  and should be returned as-is.
      //
      
      //
      //  commentChar() specifies the to-eoln-comment char
      //
      commentChar( '#' );
      slashSlashComments( true );
      slashStarComments(  true );
   }
   
   int tok = 0;
   
   public JailVector parse() throws Exception
   {
   	JailVector list = new JailVector();
   	
      LOOP: while( (tok = nextToken()) != TT_EOF ) try {
      switch( tok )
      {
      	case '(':  
      	case '{':  list.addElement( parse() );              break;
      	
      	
      	/*
      	   If you'd rather treat open-brackets as a normal list
      	   constructor, then uncomment this case:
      	   
        	case '[': list.addElement( parse() );              break;
        	
        	   Otherwise, we treat it more like an array index.
      	*/
      	case '[':  //
      	           //   This is an array index set.
      	           //   (1) Parse the array arguments into a new vector
      	           //   (2) Move the last element in 'list' to the front of
      	           //       the new vector.
      	           //
      	           //   Example:
      	           //
      	           //   (foo bar baz [ 1 2 3 ])  should turn into
      	           //   (foo bar (baz 1 2 3))
      	           //
      	           JailVector v1 = parse();
      	           v1.insertElementAt( list.lastElement(), 0 );
      	           list.removeElement( list.lastElement() );
      	           list.addElement( v1 );
      	           break;
      	
      	
      	case '`':  JailVector v2 = parse();
      	           v2.insertElementAt( new JailAtom("join"), 0 );
      	           list.addElement( v2 );
      	           break;
      	           
      	           
      	           
      	           // block-terminators might be a GREAT place to check 
      	           // the Jail library for registered functions, and 
      	           // call any special parsing code they might have...
      	                
      	case ')':  break LOOP;
      	case '}':  break LOOP;
      	case ']':  break LOOP;
      	
      	
      	case '\'': list.addElement( new JailAtom( sval ) ); break;
      	case '"':  list.addElement( new JailAtom( sval ) ); break;
      	
      	
      	case TT_NUMBER: list.addElement( new JailAtom( nval ) ); break;
      	
      	case TT_WORD:   if ( list.isEmpty() )
      	                {
                            list = new JailVector();
       	                }
      	                   
      	                list.addElement( new JailAtom( sval ) ); break;
      }
      } catch( Exception e )
      {
         System.err.println( "Parse error at line " + rdr.getLineNumber() );
      }
            
      return list;
   }
   
   public static JailVector parse( Reader r ) throws Exception
   {
      return new JailParser( r ).parse();
   }
   
   public static void main( String[] args ) throws Exception
   {
      for( int i=0; i<args.length; i++ )
         System.out.println
         ( 
            (new JailParser
               (new FileReader( args[i] ) )).parse() );
   }
}