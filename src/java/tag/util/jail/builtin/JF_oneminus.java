package tag.util.jail.builtin;

import java.io.*;
import java.util.*;
import tag.util.*;
import tag.util.jail.fw.*;
import tag.util.jail.*;

/**
*
*   Returns the provided value - 1.<br><br>
*
*   (1- fubar)    // returns fubar - 1 <br>
*   (1-)          // returns $_ - 1    <br>
*
*/
public class JF_oneminus extends JF_oneplus implements Culpable, Cloneable
{	   
      int delta = -1;
}
