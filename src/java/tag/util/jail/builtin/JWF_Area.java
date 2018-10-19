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
*   JWF_Area: defines a label and textarea combination:
*
*   (GuiArea My_Label Field_Length)
*
*   Reading the TextArea value is done like so:
*
*   (GuiArea area_id) - returns a JailVector
*   
*   Assigning a value to the Label and TextArea is done like so:
*
*   (GuiArea area_id New_Label (New_Text))
*   
*/
public class JWF_Area extends JWF_Label implements Culpable, Cloneable
{
      JTextArea    area;
      JScrollPane  scrollpane; // = new JScrollPane( textarea );
            
   void setArea( String size )
   {
      int s = 0;
      try
      {
         s = Integer.parseInt( size );
      }
      catch( Exception e ) {}
      
      area = new JTextArea( 5, s );
      scrollpane = new JScrollPane( area );
   }
   
   public int getHeight()
   {
      return area.getRows() * 25;
   }
   
   public int getWidth()
   {
      return super.getWidth()
           + area.getColumns() * 20;
   }
   
   void buildPanel()
   {
      panel = new JPanel( new GridLayout( 0, 2 ) );
      panel.add( label );
      panel.add( scrollpane );
   }
   
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
      JailVector out = new JailVector();
      
      JWF_Area jwf = (JWF_Area) Jail.getContext().get( getArg(1).toString() );
      
      StringTokenizer tok = new StringTokenizer( jwf.area.getText(), "\n" );
      
      //
      //   Should tok.nextElement() be evaluated?
      //
      while( tok.hasMoreElements() )
         out.addElement( new JailAtom( tok.nextElement().toString() ) );
         
      return out;
   }

   Culpable create()
   {
      setLabel( getStringArg(1) );
      setArea(  getStringArg(2) );
      buildPanel();
      return this;
   }   

   Culpable set()
   {
      JWF_Area jwf = (JWF_Area) Jail.getContext().get( getArg(1).toString() );
      String newLabel = getStringArg(2);
      JailVector newText = (JailVector) getArg(3);
      
      if ( newLabel.equals( "_" ) == false )
         jwf.label.setText( newLabel );
      
      jwf.area.setText( newText.toString()  );
         
      return jwf;
   }
}