package tfw.tsm;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CdlFuture<T> implements TfwFuture<T>
{
	private boolean done = false;
	private T result = null;
	
	public synchronized T get() throws Exception
	{
		if (!done)
		{
			wait();
		}
		
		return(result);
	}
	
	public synchronized T get(long timeoutMillis) throws Exception
	{
		if (!done)
		{
			wait(timeoutMillis);
		}
		
		return(result);
	}
	
	public synchronized boolean isDone()
	{
		return(done);
	}
	
	public synchronized void setResultAndRelease(T result)
	{
		if (done)
		{
			throw new IllegalStateException("Result has already been set!");
		}
		
		this.result = result;
		this.done = true;
		
		notifyAll();
	}
	
	public boolean cancel(boolean mayInterruptIfRunning)
	{
		return(false);
	}
	
	public boolean isCancelled()
	{
		return(false);
	}
}