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
package tfw.stream.doubleis;

import java.io.IOException;
import tfw.check.Argument;

public class DoubleInputStreamScalarMultiply
{
	public static DoubleInputStream create(DoubleInputStream doubleInputStream,
		double value)
	{
		Argument.assertNotNull(doubleInputStream, "doubleInputStream");
		
		return new MyDoubleInputStream(doubleInputStream, value);
	}
	
	private static class MyDoubleInputStream implements DoubleInputStream
	{
		private final DoubleInputStream doubleInputStream;
		private final double value;
		
		MyDoubleInputStream(DoubleInputStream doubleInputStream, double value)
		{
			this.doubleInputStream = doubleInputStream;
			this.value = value;
		}

		public int available() throws IOException
		{
			return doubleInputStream.available();
		}

		public void close() throws IOException
		{
			doubleInputStream.close();
		}

		public int read(double[] array) throws IOException
		{
			return read(array, 0, array.length);
		}

		public int read(double[] array, int offset, int length)
			throws IOException
		{
			int elementsRead = doubleInputStream.read(array, offset, length);
			
			for (int i=0 ; i < elementsRead ; i++)
			{
				array[offset+i] *= value;
			}
			
			return elementsRead;
		}

		public long skip(long n) throws IOException
		{
			return doubleInputStream.skip(n);
		}
	}
}