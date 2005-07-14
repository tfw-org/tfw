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

import java.io.IOException;
import java.io.OutputStream;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public class ByteIlaOutputStream extends OutputStream
{
	private final OutputStream outputStream;
	
	private boolean streamClosed = false;
	
	public ByteIlaOutputStream(OutputStream outputStream)
	{
		Argument.assertNotNull(outputStream, "outputStream");
		
		this.outputStream = outputStream;
	}
	
	public void write(byte[] b) throws IOException
	{
		checkClosed();
		
		outputStream.write(b);
	}
	
	public void write(int i) throws IOException
	{
		checkClosed();
		
		outputStream.write(i);
	}
	
	public void write(byte[] b, int offset, int length) throws IOException
	{
		outputStream.write(b, offset, length);
	}
	
	public void writeByteIla(ByteIla byteIla, int bufferSize)
		throws IOException, DataInvalidException
	{
		checkClosed();
		
		int bytesToWrite = (byteIla.length() > bufferSize) ?
			bufferSize : (int)byteIla.length();
		byte[] b = new byte[bytesToWrite];
		
		for (long i=0 ; bytesToWrite > 0 ; )
		{
			byteIla.toArray(b, 0, i, bytesToWrite);
			write(b, 0, bytesToWrite);
			i += bytesToWrite;
			
			bytesToWrite = ((byteIla.length() - i) > b.length) ?
				b.length : (int)(byteIla.length() - 1);
		}
	}
	
	public void flush() throws IOException
	{
		checkClosed();
		
		outputStream.flush();
	}
	
	public void close() throws IOException
	{
		checkClosed();
		
		outputStream.close();
		
		streamClosed = true;
	}
	
	private final void checkClosed()
	{
		if (streamClosed)
			throw new IllegalStateException(
				"Stream is closed!");
	}
}