/*
 * The Framework Project
 * Copyright (C) 2005 Anonymous
 * 
 * This library is free software; you can
 * redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your
 * option) any later version.
 * 
 * This library is distributed in the hope that it
 * will be useful, but WITHOUT ANY WARRANTY;
 * witout even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.  See the GNU Lesser General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU
 * Lesser General Public License along with this
 * library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307 USA
 */
package tfw.immutable.ila.byteila;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
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
		
	    protected synchronized void toArrayImpl(byte[] array, int offset,
	    	long start, int length)
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
    			raf.readFully(array, offset, length);
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
	    
	    public Map getParameters()
	    {
	    	HashMap map = new HashMap();
	    	
	    	map.put("name", "ByteIlaFromFile");
	    	map.put("file", file);
	    	map.put("length", new Long(length()));
	    	
	    	return(map);
	    }
	}
	
	private static class TimerRunnable implements Runnable
	{
		private boolean resetTimer = true;
		private Thread thread = null;
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