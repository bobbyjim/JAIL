package tag.util.jail.builtin;

import java.io.*;
import java.util.*;
import tag.util.*;
import tag.util.jail.fw.*;
import tag.util.jail.*;


/************************************************************************
*
*   JF_httpGetCookie: retrieves the HTTP cookie from a list of headers.
*   The cookie value is returned, without trailing path information, 
*   and the 'Set-cookie' value changed to just plain 'Cookie'.
*
*   Example:
*
*   (http.cookie headers)
*
*   If the header block contained this cookie:
*
*   Set-cookie: JSESSIONID=web-server-75%253A3d0c2298%253A82417eb267c1791;path=/prov
*
*   Then this is returned:
*
*   Cookie: JSESSIONID=web-server-75%253A3d0c2298%253A82417eb267c1791
*
************************************************************************/
public class JF_httpGetCookie extends JailFunction implements Culpable, Cloneable
{
   protected String[][] getSyntax()  { return JailFunction.NULLSYNTAX; }

   protected boolean evaluateArguments() 
   {
      return true;
   }
   
   protected Culpable applyCommand()    
   {
      JailVector hdrs   = (JailVector) getArg(1).eval();
      String     cookie = null;
      
      for( int i=0; i<hdrs.size(); i++ )
         if ( hdrs.elementAt(i).toString().indexOf( "Set-cookie" ) > -1 )
         {
            cookie = "Cookie" + hdrs.elementAt(i).toString().substring( 10 );
            if ( cookie.indexOf( ';' ) > -1 )
               cookie = cookie.substring( 0, cookie.indexOf( ';' ) );
            break;
         }
      
      if ( cookie == null )
         return JF_eq.FALSE;
      else
         return new JailAtom( cookie );
   }
}
