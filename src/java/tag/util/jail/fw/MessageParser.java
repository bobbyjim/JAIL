package tag.util.jail.fw;

import java.io.*;
import java.util.*;

public interface MessageParser
{
	public void   parseInput(   InputStream input );
	public Vector buildCommand();
	public void   write( OutputStream out, Object response );
}