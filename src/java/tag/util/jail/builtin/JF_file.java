package tag.util.jail.builtin;

import java.io.*;
import java.util.*;
import tag.util.*;
import tag.util.jail.fw.*;
import tag.util.jail.*;

/**
*
*   File ops
*
*   (file fname)       # returns size of file, 0 if nonexistant
*   (file <fname)      # returns array of lines in file
*   (file >fname dat)  # creates file containing dat
*   (file >>fname dat) # appends dat to file
*   (file !fname)      # parses in lines of file
*
*/
public class JF_file extends JailFunction implements Culpable, Cloneable
{
      final int SIZE   = 0;
      final int READ   = 1;
      final int WRITE  = 2;
      final int APPEND = 3;
      final int PARSE  = 4;
      
   protected String[][] getSyntax()  { return JailFunction.NULLSYNTAX; }
   protected boolean evaluateArguments() { return true; }
   
   protected Culpable applyCommand()    
   {
      String   fname = getStringArg(1);      
      String   data  = "";
      
      for( int i=2; i<size(); i++ )
         data += getStringArg( i );
      
      Culpable val = new JailAtom( -1 );
      
      int mode;
      
      if       ( fname.startsWith( "<"  ) ) mode = READ;
      else if  ( fname.startsWith( ">"  ) ) mode = WRITE;
      else if  ( fname.startsWith( ">>" ) ) mode = APPEND;
      else if  ( fname.startsWith( "!"  ) ) mode = PARSE;
      else                                  mode = SIZE;
      
      boolean append = false;
      
      try
      {
         switch( mode )
         {
            case SIZE:    File f = new File( fname );
                          val = new JailAtom( f.length() );
                          break;
                    

            case PARSE:   fname = fname.substring( 1 );
                          try
                          {
                             val = Jail.getInstance().run( new FileReader( fname ) );
                             val = (Culpable)((JailVector)val).lastElement();
                          }
                          catch( Exception e )
                          {
                             e.printStackTrace();
                          }
                         
                          break;
                          
                          
            case READ:    fname = fname.substring( 1 );
                          //System.err.println( "reading " + fname );
                          BufferedReader in = new BufferedReader
                          (
                             new FileReader( fname )
                          );
                          JailVector out = new JailVector();
                    
                          String line;
                          while( (line = in.readLine()) != null )
                             out.addElement( new JailAtom( line ) );
                       
                          val = out;
                          break;
                    

            case APPEND:  fname  = fname.substring( 1 );
                          //System.err.println( "append mode" );
                          append = true;
                          // fall through
                          
            case WRITE:   fname  = fname.substring( 1 );
                          //System.err.println( "writing " + fname );
                          PrintWriter pw = new PrintWriter
                          (
                             new FileOutputStream( fname, append )
                          );
                          pw.print( data );
                          pw.close();
                          
                          // return the file size
                          
                          File g = new File( fname );
                          val = new JailAtom( g.length() );
                          break;
         }
      }
      catch( Exception e ) {}
      
      return val;
   }
}
