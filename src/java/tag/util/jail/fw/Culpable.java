package tag.util.jail.fw;

import java.io.*;
import java.util.*;

public interface Culpable extends Serializable
{
   public Culpable parse();
	public Culpable eval();
	public Object   clone();
	public String   toString();
	public String   toString( String delimiter );
	public String   explain();
}
