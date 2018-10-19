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
*   JWF_Field: defines a label and textfield combination:
*
*   (gui.field My_Label Field_Length)
*
*   Reading the TextField value is done like so:
*
*   (gui.field field_id)
*   
*   Assigning a value to the Label and TextField is done like so:
*
*   (gui.field field_id New_Label TextField_Name)
*   
*/
public class JWF_Field extends JWF_Label implements Culpable, Cloneable
{
      JTextField component;
      
   void setComponent( String size )
   {
      int s = 0;
      try
      {
         s = Integer.parseInt( size );
      }
      catch( Exception e ) {}
      
      component = new JTextField( s );
   }
      
   public int getWidth()
   {
      return super.getWidth()
           + component.getColumns() * 20;
   }
   
   void buildPanel()
   {
      panel = new JPanel( new GridLayout( 0, 2 ) );
      panel.add( label );
      panel.add( component );
   }

   protected String[][] getSyntax()  { return JailFunction.NULLSYNTAX; }
   protected boolean evaluateArguments() { return true; }
   

   protected Culpable applyCommand()    
   {
      switch( size() )
      {
         case 2: return get();         
         case 3: return create();                                   
         case 4: return set();
      }
      return null;
   }
   
         
   Culpable get()
   {
      JWF_Field jwf = (JWF_Field) Jail.getContext().get( getArg(1).toString() );
      return new JailAtom( jwf.component.getText() );
   }
   
   Culpable create()
   {
      setLabel( getStringArg(1) );
      setComponent( getStringArg(2) );
      buildPanel();
      return this;
   }
   
   Culpable set()
   {
      String newLabel = getStringArg(2);
      String newText  = getStringArg(3);
      
      JWF_Field jwf = (JWF_Field) Jail.getContext().get( getArg(1).toString() );
      
      if ( newLabel.equals( "_" ) == false )
         jwf.label.setText( newLabel );
      
      if ( newText.equals( "_" ) == false )
         jwf.component.setText( newText  );
         
      return jwf;
   }
}