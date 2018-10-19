package tag.util.jail.fw;

import java.io.*;
import java.net.*;
import java.util.*;

import tag.util.jail.builtin.*;
import tag.util.jail.*;

/************************************************************

   This is the base class for making remote queries
   to Jail.
   
************************************************************/
public class Query extends JailVector implements Questionable
{
      transient Socket client;
      
   /*********************************************************
   *
   *   This method is called by the client.
   *
   *   This is a convenience method for setting list data
   *   in the query.
   *
   *********************************************************/
   public void setQueryData( String[] args )
   {
      for( int i=0; i<args.length; i++ )
         addElement( new JailAtom( args[i] ) );
   }
   
   /*********************************************************
   *
   *   This method is called by the client.
   *
   *   This method creates the client socket,
   *   write the query to the host,
   *   reads the response from the host,
   *   and returns the response.
   *
   *********************************************************/
   public Object getResponse( String host, int port )
   throws Exception
   { 
      client = new Socket( host, port );
      
      ObjectOutputStream oos = new ObjectOutputStream
      (
         client.getOutputStream()
      );
      
      oos.writeObject( this );
      
      ObjectInputStream ois = new ObjectInputStream
      (
         client.getInputStream()
      );
      
      Object out = ois.readObject();
      
      return out;      
   }

   
   /*********************************************************
   *
   *   This method is called by the server.
   *
   *   This method handles the query on the server side.
   *
   *********************************************************/
   public Culpable handle()
   {
      return this.eval();
   }
}
