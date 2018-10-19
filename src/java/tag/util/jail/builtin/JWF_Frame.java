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
*   JWF_Frame: defines a frame and its members.<br>
*   
*   (gui.frame title element-vector)
*
*/
public class JWF_Frame extends JWF implements Culpable, Cloneable
{
      JFrame frame;
      
   protected String[][] getSyntax()  { return JailFunction.NULLSYNTAX; }
   protected boolean evaluateArguments() { return true; }
   
   JFrame createFrame( String title )
   {
      JFrame frame = new JFrame( title );
      frame.addWindowListener( new WindowAdapter() 
         {
            public void windowClosing(WindowEvent e) 
            {
               //System.exit(0);
            }
         }
      );     
      return frame; 
   }
   
   public int getHeight() 
   {
      return frame.getHeight();
   }
   
   public int getWidth()
   {
      return frame.getWidth();
   }
   
   public Component getComponent()
   {
      return frame;
   }
   
   protected Culpable applyCommand()    
   {
      String     title    = getStringArg(1);
      JailVector elements = getVectorArg(2);

      //System.err.println( "Title: " + title );
      //System.err.println( "Elems: " + elements );
      
      frame = createFrame( title );
      JPanel panel = new JPanel( new GridLayout( 0, 1 ) );
            
      int height = 0;
      int width  = 0;
      
      for( int i=0; i<elements.size(); i++ )
      {
         JWF       a1 = (JWF)((Culpable) elements.elementAt(i)).eval();
         Component a2 = a1.getComponent();

         panel.add( a2 );
         
         height += a1.getHeight();
         width   = ( width < a1.getWidth() )? a1.getWidth(): width;
      }
      
      frame.getContentPane().add( panel );
      frame.setSize( width, height );
      frame.setVisible( true );
      
      return this;
   }
}