package tag.util.jail.builtin;

import java.net.*;
import java.util.*;
import tag.util.*;
import tag.util.jail.fw.*;
import tag.util.jail.*;

/************************************************************************
*
*   JF_decode returns the URL-decoding of a string.
*
*   (decode string)
*
************************************************************************/
public class JF_decode extends JailFunction implements Culpable, Cloneable
{
   protected String[][] getSyntax()  { return JailFunction.NULLSYNTAX; }
   protected boolean evaluateArguments() { return true; }
   
   protected Culpable applyCommand()    
   {
      try
      {
         String arg = getStringArg(1);
         return new JailAtom( URLDecoder.decode( arg, "UTF-8" ) );
      }
      catch( Exception uce ) 
      {
         System.err.println( "ERROR in JF_decode: " + uce );
         return null;
      }
   }
}
