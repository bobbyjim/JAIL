package tag.util.jail.builtin;

import java.net.*;
import java.util.*;
import tag.util.*;
import tag.util.jail.fw.*;
import tag.util.jail.*;

/************************************************************************
*
*   JF_encode returns the URL-encoding of a string.
*
*   (encode string)
*
************************************************************************/
public class JF_encode extends JailFunction implements Culpable, Cloneable
{
   protected String[][] getSyntax()  { return JailFunction.NULLSYNTAX; }
   protected boolean evaluateArguments() { return true; }
   
   protected Culpable applyCommand()    
   {
      try
      {
         return new JailAtom( HttpMessage.encode( getStringArg(1) ) );
      }
      catch( Exception uce ) 
      {
         System.err.println( "ERROR in JF_encode: " + uce );
         return null;
      }
   }
}
