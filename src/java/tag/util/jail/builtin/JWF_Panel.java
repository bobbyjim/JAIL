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
*   JWF_Panel: defines a panel and its members.
*   
*/
public class JWF_Panel extends JWF implements Culpable, Cloneable
{
      JPanel panel;
      
   protected String[][] getSyntax()  { return JailFunction.NULLSYNTAX; }
   protected boolean evaluateArguments() { return true; }
   
   public int getHeight() 
   {
      return panel.getHeight();
   }
   
   public int getWidth()
   {
      return panel.getWidth();
   }
   
   public Component getComponent()
   {
      return panel;
   }
   
   protected Culpable applyCommand()    
   { 
      {
         panel = new JPanel( new GridLayout( 1, 0 ) );
            
         int height = 0;
         int width  = 0;
      
         for( int i=1; i<size(); i++ )
         {
            JWF       a1 = (JWF)((Culpable) elementAt(i)).eval();
            Component a2 = a1.getComponent();

            panel.add( a2 );
         
            height  = ( height < a1.getHeight() )? a1.getHeight(): height;
            width   = ( width  < a1.getWidth()  )? a1.getWidth() : width;
         }
      }
      return this;
   }
}