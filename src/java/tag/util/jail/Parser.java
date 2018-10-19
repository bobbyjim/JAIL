package tag.util.jail;

import java.io.*;
import java.util.*;
import tag.util.*;

public class Parser extends StreamTokenizer
{
   public Parser( Reader fr )
   {
      super( fr );
   		
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
   
   public Vector parse() throws Exception
   {
   	Vector list = new Vector();
   	
      LOOP: while( (tok = nextToken()) != TT_EOF ) switch( tok )
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
      	           Vector v1 = parse();
      	           v1.insertElementAt( list.lastElement(), 0 );
      	           list.removeElement( list.lastElement() );
      	           list.addElement( v1 );
      	           break;
      	
      	
      	case '`':  Vector v2 = parse();
      	           v2.insertElementAt( "join", 0 );
      	           list.addElement( v2 );
      	           break;
      	           
      	case ')':  break LOOP;
      	case '}':  break LOOP;
      	case ']':  break LOOP;
      	case '\'': list.addElement( sval ); break;
      	case '"':  list.addElement( sval ); break;
      	
      	
      	case TT_NUMBER: list.addElement( "" + nval ); break;
      	
      	case TT_WORD:   if ( list.isEmpty() )
      	                   list = new Vector();
      	                   
      	                list.addElement( sval ); break;
      }
            
      return list;
   }
   
   public static Vector parse( Reader r ) throws Exception
   {
      return new Parser(r).parse();
   }
   
   public static void main( String[] args ) throws Exception
   {
      for( int i=0; i<args.length; i++ )
         System.out.println( (new Parser( new FileReader( args[i] ) )).parse() );
   }
}