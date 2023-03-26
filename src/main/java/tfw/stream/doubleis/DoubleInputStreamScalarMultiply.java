package tfw.stream.doubleis;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

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

		public long available() throws DataInvalidException
		{
			return doubleInputStream.available();
		}

		public void close() throws DataInvalidException
		{
			doubleInputStream.close();
		}

		public int read(double[] array) throws DataInvalidException
		{
			return read(array, 0, array.length);
		}

		public int read(double[] array, int offset, int length)
			throws DataInvalidException
		{
			int elementsRead = doubleInputStream.read(array, offset, length);
			
			for (int i=0 ; i < elementsRead ; i++)
			{
				array[offset+i] *= value;
			}
			
			return elementsRead;
		}

		public long skip(long n) throws DataInvalidException
		{
			return doubleInputStream.skip(n);
		}
	}
}