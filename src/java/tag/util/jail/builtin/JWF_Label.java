package tag.util.jail.builtin;

import java.util.*;
import tag.util.*;
import tag.util.jail.fw.*;
import tag.util.jail.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
*
*   JWF_Label: defines a label:
*
*   (GuiLabel My_Label)
*
*   Reading the label value is done like so:
*
*   (GuiLabel label_id)
*   
*   Assigning a value to the Label is done like so:
*
*   (GuiLabel label_id new_label_text)
*   
*/
public class JWF_Label extends JWF implements Culpable, Cloneable
{
      JLabel     label;      
   
   void setLabel( String labeltext )
   {
      label = new JLabel( labeltext );
   }
   
   public int getHeight()
   {
      return 25;
   }
   
   public int getWidth()
   {
      return label.getText().length() * 20;
   }
   
   void buildPanel()
   {
      panel = new JPanel( new GridLayout( 0, 1 ) );
      panel.add( label );
   }
      
   protected String[][] getSyntax()  { return JailFunction.NULLSYNTAX; }
   protected boolean evaluateArguments() { return true; }
   

   protected Culpable applyCommand()    
   {
      switch( size() )
      {
         case 2: return get();         
         case 3: return set();
      }
      return null;
   }
   
         
   Culpable get()
   {
      JWF_Label jwf = (JWF_Label) Jail.getContext().get( getArg(1).toString() );
      
      if ( jwf == null ) // create
      {
         setLabel( getStringArg(1) );
         buildPanel();
         return this;
      }
      else
         return new JailAtom( jwf.label.getText() );
   }
   
  
   Culpable set()
   {
      String newLabel = getStringArg(2);
      
      JWF_Label jwf = (JWF_Label) Jail.getContext().get( getArg(1).toString() );
      
      if ( newLabel.equals( "_" ) == false )
         jwf.label.setText( newLabel );
      
      return jwf;
   }
}