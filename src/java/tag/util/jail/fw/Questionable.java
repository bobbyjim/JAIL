package tag.util.jail.fw;

import java.io.*;

/************************************************************

   This is the interface for making remote queries
   to Jail.
   
************************************************************/
public interface Questionable extends Serializable
{
   public void     setQueryData( String[] args ) throws Exception;
   public Object   getResponse( String host, int port ) throws Exception;
   public Culpable handle();
}