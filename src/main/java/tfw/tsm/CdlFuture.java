package tfw.tsm;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CdlFuture<T> implements Future<T>
{
	private boolean done = false;
	private T result = null;
	
	public synchronized T get() throws InterruptedException
	{
		if (!done)
		{
			wait();
		}
		
		return(result);
	}
	
	public synchronized T get(long timeout, TimeUnit unit)
		throws InterruptedException, TimeoutException
	{
		if (!done)
		{
			wait(unit.toMillis(timeout));
		}
		
		return(result);
	}
	
	public synchronized boolean isDone()
	{
		return(done);
	}
	
	synchronized void setResultAndRelease(T result)
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