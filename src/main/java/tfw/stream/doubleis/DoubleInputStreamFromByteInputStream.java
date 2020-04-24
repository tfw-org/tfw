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
 * without even the implied warranty of
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
package tfw.stream.doubleis;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.stream.byteis.ByteInputStream;

public class DoubleInputStreamFromByteInputStream
{
	public static DoubleInputStream create(ByteInputStream byteInputStream)
	{
		Argument.assertNotNull(byteInputStream, "byteInputStream");
		
		return new MyDoubleInputStream(byteInputStream);
	}
	
	private static class MyDoubleInputStream implements DoubleInputStream
	{
		private final ByteInputStream byteInputStream;
		private final byte[] buffer = new byte[1000];
		
		public MyDoubleInputStream(ByteInputStream byteInputStream)
		{
			this.byteInputStream = byteInputStream;
		}

		@Override
		public long available() throws DataInvalidException
		{
			return byteInputStream.available();
		}

		@Override
		public void close() throws DataInvalidException
		{
			byteInputStream.close();
		}

		@Override
		public int read(double[] array) throws DataInvalidException
		{
			return read(array, 0, array.length);
		}

		@Override
		public synchronized int read(double[] array, int offset, int length)
			throws DataInvalidException
		{
			int totalElementsRead = 0;
			
			for (int i=0 ; i < length ; i++)
			{
				int cachePosition = i % buffer.length;
				
				if (cachePosition == 0)
				{
					int remaining = length - i;
					int elementsRead = byteInputStream.read(buffer, 0,
						buffer.length < remaining ? buffer.length : remaining);
					
					if (elementsRead > 0)
					{
						totalElementsRead += elementsRead;
					}
				}
				
				array[offset + i] = (double)buffer[cachePosition];
			}
			
			return totalElementsRead;
		}

		@Override
		public long skip(long n) throws DataInvalidException
		{
			return byteInputStream.skip(n);
		}
	}
}