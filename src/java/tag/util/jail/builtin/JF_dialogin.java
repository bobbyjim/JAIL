package tag.util.jail.builtin;

import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

import tag.util.*;
import tag.util.jail.fw.*;
import tag.util.jail.*;

/**
*
*   This class pops up a JDialog with an input field.<br>
*   The input field text is returned.<br><br>
*
*   (dialogin msg...)<br>
*/
public class JF_dialogin extends JailFunction implements Culpable, Cloneable
{
	   JTextField field = new JTextField(  );
	   JButton    ok    = new JButton( "Ok" );
	
   protected String[][] getSyntax()  { return JailFunction.NULLSYNTAX; }
   protected boolean evaluateArguments() { return true; }
   
   protected Culpable applyCommand()    
   {
   	String  msg      = "";
   	boolean blocking = true;
   	
   	if ( size() == 1 )
   	   msg = ((Culpable) Jail.getContext().get( "$_" )).toString();
   	else
   	   for( int i=1; i<size(); i++ )
   	      msg += ((Culpable) elementAt(i)).eval().toString();
      
      final JDialog dialog = new JDialog( (JFrame)null, "Jail Input Dialog", blocking );

		ok.addActionListener( new ActionListener()
		   {
		   	public void actionPerformed( ActionEvent e )
		   	{
		   		dialog.dispose();
		   	}
		   }
      );
      
      dialog.getContentPane().setLayout( new GridLayout( 0, 1 ) );      
      dialog.getContentPane().add( new JLabel( msg ) );
      dialog.getContentPane().add( field );
      dialog.getContentPane().add( ok );
      
      field.setText("");
      dialog.setSize( 300, 125 );
      dialog.setLocation( 300, 200 );
      dialog.show();
   	//
   	//  Return the size of the list echoed...
   	//
   	return new JailAtom( field.getText() );
   }
}
