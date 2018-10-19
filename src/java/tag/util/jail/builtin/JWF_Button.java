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
*   JWF_Button: creates a Button:
*
*   (GuiButton Button-Label Event-Handler)
*   
*   where Event-Handler is a block of executable code which
*   runs when the button is pressed.
*
*/
public class JWF_Button extends JWF implements Culpable, Cloneable
{   
      JailButton button;
       
   public int getHeight()
   {
      return 25;
   }
   
   public int getWidth()
   {
      return button.getText().length() * 20;
   }
   
   protected String[][] getSyntax()  { return JailFunction.NULLSYNTAX; }
   protected boolean evaluateArguments() { return true; }
   
   public Component getComponent()
   {
      return button;   
   }
   
   protected Culpable applyCommand()    
   {
      Culpable out = null;
      int pos = 0;
      if ( getStringArg(pos).equals( "gui.button" ) )
         pos++;
         
      switch( size() - pos )
      {
         case 1:  out = get( getStringArg(pos) );
         case 2:  out = setText( getStringArg(pos), getStringArg(pos+1) );
         default: out = set( getStringArg(pos+1), getArg(pos+2) );
      }
      return out;
   }
   
   Culpable get( String id )
   {
      System.err.println( "Getting text" );
      if ( id.equals( "gui.button" ) ) 
         return null;
         
      JWF_Button jwf = (JWF_Button) Jail.getContext().get( id );
      
      if ( jwf == null )
         return null;
         
      return new JailAtom( jwf.button.getText() );
   }
   
   Culpable setText( String id, String label )
   {
      System.err.println( "Changing text" );
      if ( id.equals( "gui.button" ) ) 
         return null;
      
      JWF_Button jwf = (JWF_Button) Jail.getContext().get( id );
      
      if ( jwf == null )
         return null;
         
      jwf.button.setText( label );
      return new JailAtom( label );
   }
   
   Culpable set( String label, Culpable h )
   {
      System.err.println( "New button \"" + label + "\"" );
      button = new JailButton( label, h );
      return this;
   }

   class JailButton extends JButton
   {
         final Culpable handler;
         
      JailButton( String label, Culpable h )
      {
         super( label );
         this.handler = h;
         this.addActionListener( new ActionListener()
            {
               public void actionPerformed( ActionEvent e )
               {
                  Culpable code = (Culpable) handler.clone();
                  code.eval();
               }
            }
         );
      }
   }
}