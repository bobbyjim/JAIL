package tag.util.jail.builtin;

import java.util.*;
import tag.util.*;
import tag.util.jail.fw.*;
import tag.util.jail.*;

/**
*
*   Adds numbers together.<br><br>
*
*   (add n m)     - returns n+m<br>
*   (add n m o p) - returns n+m+o+p<br>
*
*   Adds elements to vectors and hashes.
*
*   (add myVector (elem ...))
*   (add myHash   ((k v)...))
*
*/
public class JF_add extends JailFunction implements Culpable, Cloneable
{
   protected String[][] getSyntax()  { return JailFunction.NULLSYNTAX; }
   protected boolean evaluateArguments() { return true; }
   
   protected Culpable applyCommand()    
   {
      Culpable key = getArg(1).eval();
      //Culpable obj = (Culpable) Jail.getContext().get( key.toString() );
          
//      if ( obj instanceof JailAtom   ) return numeric();
//      if ( obj instanceof JailVector ) return vector( (JailVector) obj, getVectorArg(2) );
//      if ( obj instanceof JailHash   ) return hash( (JailHash) obj, getVectorArg(2) );

      if ( key instanceof JailAtom   ) return numeric();
//      if ( key instanceof JailVector ) return vector( (JailVector) key, getArg(2) );
//      if ( key instanceof JailHash   ) return hash( (JailHash) key, getArg(2) );
            
      return (Culpable) firstElement();
   }
   
   Culpable numeric()
   {  
      double num = 0.0;
      
      for( int i=1; i<size(); i++ )
      {
         JailAtom n = (JailAtom) getArg(i).eval();
         num += n.d;
      }
      
      return new JailAtom( num );
   }
   
/*
   Culpable vector( JailVector obj, Culpable arg )
   {
      obj.addMembers( arg );
      return obj;
   }
*/

/* 
   Culpable hash( JailHash obj, Culpable arg )
   {
      for( int i=0; i<jv.size(); i++ )
         obj.put( (JailVector) jv.elementAt(i) );
      return obj;
   }
*/
}
