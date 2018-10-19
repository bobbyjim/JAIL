package tag.util.jail.builtin;

import java.util.*;
import javax.swing.*;
import java.awt.event.*;

import tag.util.*;
import tag.util.jail.fw.*;
import tag.util.jail.*;

/**
*
*   This class pops up a JDialog.<br>
*   The size of the dialog list is returned.<br><br>
*
*   (dialog msg...)<br>
*/
public class JF_dialog extends JailFunction implements Culpable, Cloneable
{
   String getDesc()
   {
      return "Pops up a JDialog.";
   }
   
   String getParms()
   {
      return "msg";
   }


   protected String[][] getSyntax()  { return JailFunction.NULLSYNTAX; }
   protected boolean evaluateArguments() { return true; }
   
   protected Culpable applyCommand()    
   {
   	String msg = "";
   	
   	if ( size() == 1 )
   	   msg = ((Culpable) Jail.getContext().get( "$_" )).toString();
   	else
   	   for( int i=1; i<size(); i++ )
   	      msg += ((Culpable) elementAt(i)).eval().toString();

      JOptionPane.showMessageDialog(null, msg);
      
   	//
   	//  Return the size of the list echoed...
   	//
   	return new JailAtom( size()-1 );
   }
}
