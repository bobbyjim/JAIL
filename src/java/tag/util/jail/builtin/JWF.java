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
*   JWF: defines a generic Jail graphical widget
*   
*/
public abstract class JWF extends JailFunction implements Culpable, Cloneable
{
      JPanel panel;
      
   public Component getComponent()
   {
      return panel;
   }
   
   public abstract int getHeight();
   public abstract int getWidth();
}