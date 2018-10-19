package tag.util.jail;

import java.io.*;
import java.util.*;
import tag.util.*;
import tag.util.jail.builtin.*;
import tag.util.jail.fw.*;

/*************************************************************************
*
*   Jail<br>
*
*   This is the Jail loader.
*
*************************************************************************/
public class Jail
{
                static  Jail      instance   = null;
	   public    static  Hashtable stringmaps = new Hashtable();
	             static  Librarian lib        = Librarian.getInstance();
	                     
	   protected boolean debug = false;
	   
	   static
	   {
	   	stringmaps.put( "\\n", new JailAtom( "\n" ) );
	   	stringmaps.put( "\\s", new JailAtom( " "  ) );
	   };
	   
	   private Vector registry = new Vector();

/*	   
	   public static String DATA="DATA";
	   public static String HASH="HASH";
	   public static String FUNC="FUNC";
*/
   
   private Jail()
   {
      /*
      register( "*",        new JF_mul()      );
      register( "+",        new JF_add()      );
      register( "++",       new JF_inc()      );
      register( "-",        new JF_sub()      );
      register( "--",       new JF_dec()      );
      register( "/",        new JF_div()      );
      register( "1+",       new JF_oneplus()  );
      register( "1-",       new JF_oneminus() );      
      register( "?",        new JF_echo()     );
      */
      register( "!=",           new JF_notequals());
      register( "=",            new JF_define()   );
      register( "==",           new JF_eq()       );
      register( "add",          new JF_add()      );
      register( "atom",         new JF_Atom()     );
      register( "car",          new JF_car()      );
      register( "cat",          new JF_cat()      );
      register( "cli",          new JF_cli()      );
      register( "debug",        new JF_debug()    );
      register( "dec",          new JF_dec()      );
      register( "define",       new JF_define()   );
      register( "dialog",       new JF_dialog()   );
      register( "dialogin",     new JF_dialogin() );
      register( "div",          new JF_div()      );
      register( "do",           new JF_do()       );
      register( "eq",           new JF_eq()       );
      register( "eval",         new JF_eval()     );
      register( "exit",         new JF_exit()     );
      register( "file",         new JF_file()     );
      register( "fn",           new JF_fn()       );
      register( "foreach",      new JF_foreach()  );
      register( "forever",      new JF_forever()  );
      register( "grep",         new JF_grep()     );
      register( "gt",           new JF_gt()       );
      register( "gui.area",     new JWF_Area()   );
      register( "gui.button",   new JWF_Button() );
      register( "gui.field",    new JWF_Field()  );
      register( "gui.frame",    new JWF_Frame()  );
      register( "gui.label",    new JWF_Label()  );
      register( "gui.panel",    new JWF_Panel()  );
      register( "hash",         new JF_Hash()     );
      register( "http.cookie",  new JF_httpGetCookie() );
      register( "http.fmt",     new JF_httpFormat() );
      register( "http.out",     new JF_httpSend()   );
      register( "http.props",   new JF_httpProperties() );
      register( "hurl.out",     new JF_hurlSend() );
      register( "if",           new JF_if()       );
      register( "inc",          new JF_inc()      );
      register( "isFalse",      new JF_false()    );
      register( "isTrue",       new JF_true()     );
      register( "join",         new JF_join()     );
      register( "keys",         new JF_keys()     );
      register( "len",          new JF_len()      );
      register( "list",         new JF_List()     );
      register( "lt",           new JF_lt()       );
      register( "map",          new JF_map()      );
      register( "matches",      new JF_match()    );
      register( "mul",          new JF_mul()      );
      register( "my",           new JF_define()   );
      register( "neq",          new JF_notequals());
      register( "no",           new JF_no()       ); // no entry in symbol table
      register( "or",           new JF_or()       );
      register( "pr",           new JF_echo()     ); // print
      register( "pwd",          new JF_pwd()      );
      register( "q",            new JF_List()     );
      register( "serialid",     new JF_SerialId() ); 
      register( "sleep",        new JF_sleep()    );
      register( "split",        new JF_split()    );
      register( "sub",          new JF_sub()      );
      register( "subst",        new JF_substitute() );
      register( "tcp.out",      new JF_tcpSend()  );
      register( "tcp.server",   new JF_server()   );
      register( "udp.out",      new JF_UdpSend()  );
      register( "udp.server",   new JF_UdpServer() );
      register( "undef",        new JF_undef()    );
      register( "while",        new JF_while()    );

   }
   
   public static Jail getInstance()
   {
      if ( instance == null )
         instance = new Jail();
         
      return instance;
   }

   public void setDebug( boolean mode )
   {
      debug = mode;
   }
   
   public boolean getDebug()
   {
      return debug;
   }
   
   /*******************************************************************
   *
   *   Here are some symbol table operations.
   *
   *******************************************************************/   
   public static Librarian getContext()
   {
      return lib; // .getNamespace( "Jail" );
   }
   
   public void register( String name, JailFunction jf )
   {
      Jail.getContext().put( name, jf );
      registry.addElement( name );
      
      if ( debug ) 
         System.err.println( "Registered " + name );
   }
   
   public void register( String name, Culpable c )
   {
      Jail.getContext().put( name, c );
      registry.addElement( name );
   }
   
   public Culpable fetch( String name )
   {
      return (Culpable) Jail.getContext().get( name );
   }
   
   public Enumeration getRegistry()
   {
      return registry.elements();
   }
   
   /*******************************************************************
   *
   *   Here's the runner.
   *
   *******************************************************************/   
   public Culpable run( Reader rdr )
   {
      Culpable out;
   	try
   	{
 	      out = 
  	         (new JailInterpreter()).interpret
  	         ( 
  	            (new JailParser( rdr )).parse()
  	         );
  	   }
  	   catch( Exception e )
  	   {
  	   	e.printStackTrace();
  	   	out = new JailAtom( -1 );
  	   }
  	   return out;
   }
      
   /*******************************************************************
   *
   *   Here's main().
   *
   *******************************************************************/   
   public static void main( String[] args )  throws Exception
   {
   	Jail jail = new Jail();
   	
   	jail.debug = true;
   	
   	if ( args.length > 0 )
   	   for( int i=0; i<args.length; i++ )
   	      jail.run( new FileReader( args[i] ) );
   	else
   	   jail.run( new StringReader( "(cli)" ) );
   	   
   	System.exit(0);
   }
}