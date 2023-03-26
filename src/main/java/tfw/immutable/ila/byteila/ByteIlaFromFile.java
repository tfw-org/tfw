package tfw.immutable.ila.byteila;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;

public final class ByteIlaFromFile
{
	private ByteIlaFromFile() {}
	
	public static ByteIla create(File file)
	{
		Argument.assertNotNull(file, "file");

		if (!file.exists())
			throw new IllegalArgumentException(
				"file does not exist!");
		if (!file.canRead())
			throw new IllegalArgumentException(
				"file cannot be read!");
		
		return(new MyByteIla(file));
	}
	
	private static class MyByteIla extends AbstractByteIla
		implements ImmutableProxy
	{
		private final File file;
		
		private RandomAccessFile raf = null;
		private TimerRunnable timerRunnable = null;
    			
		public MyByteIla(File file)
		{
			super(file.length());
			
			this.file = file;
		}
		
		@Override
		protected void toArrayImpl(byte[] array, int offset, int stride,
				long start, int length) throws DataInvalidException
		{
    		if (raf == null)
    		{
    			try
				{
    				raf = new RandomAccessFile(file, "r");
				}
    			catch(FileNotFoundException fnfe)
				{
    				return;
    			}
    		}
    		
    		try
			{
    			raf.seek(start);
    			if (stride == 1) {
	    			raf.readFully(array, offset, length);
    			} else {
    				final int end = offset + length;
    				for(; offset < end; offset += stride) {
    					raf.read(array, offset, 1);
    				}
    			}
			}
    		catch(IOException ioe)
			{
    			return;
    		}
    		
    		if (timerRunnable == null)
    		{
    			timerRunnable = new TimerRunnable(this);
    			
    			Thread timerThread = new Thread(timerRunnable);   			
	    		timerThread.setDaemon(true);
	    		timerThread.setPriority(Thread.MIN_PRIORITY);
	    		timerThread.start();
    		}
    		else
    		{
	    		timerRunnable.resetTimer();
    		}
	    }
	    
	    public synchronized void closeRAF()
	    {
			try
			{
				raf.close();
			}
			catch(IOException ioe) {}
		
			raf = null;
    		timerRunnable = null;
	    }
	    
	    public Map<String, Object> getParameters()
	    {
	    	HashMap<String, Object> map = new HashMap<String, Object>();
	    	
	    	map.put("name", "ByteIlaFromFile");
	    	map.put("file", file);
	    	map.put("length", new Long(length()));
	    	
	    	return(map);
	    }
	}
	
	private static class TimerRunnable implements Runnable
	{
		private boolean resetTimer = true;
		private MyByteIla myByteIla = null;
		
		public TimerRunnable(MyByteIla myByteIla)
		{
			if (myByteIla == null)
				throw new IllegalArgumentException(
					"myByteIla == null not allowed!");
			
			this.myByteIla = myByteIla;
		}
		
		public synchronized void run()
		{
			while(resetTimer)
			{
				resetTimer = false;
  				
				try
				{
					wait(10000);
				}
				catch(InterruptedException ie) {}
			}	
			myByteIla.closeRAF();
		}
		
		public synchronized void resetTimer()
		{
			resetTimer = true;
		}
	};
}