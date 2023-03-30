package tfw.immutable;

public class DataInvalidException extends Exception
{
	public DataInvalidException(String s)
	{
		super(s);
	}
	
	public DataInvalidException(String s, Throwable cause)
	{
		super(s, cause);
	}
}
