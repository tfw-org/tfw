package tfw.stream.doubleis;

import java.io.IOException;
import tfw.check.Argument;
import tfw.stream.byteis.ByteIs;

public class DoubleIsFactoryFromByteIsFactory
{
	private DoubleIsFactoryFromByteIsFactory() {}
	
	public static DoubleIsFactory create(final ByteIs byteIs)
	{
		return new DoubleIsFactoryImpl(byteIs);
	}
	
	private static class DoubleIsFactoryImpl implements DoubleIsFactory {
		private final ByteIs byteIs;
		
		public DoubleIsFactoryImpl(final ByteIs byteIs) {
			Argument.assertNotNull(byteIs, "byteIs");
			
			this.byteIs = byteIs;
		}
		@Override
		public DoubleIs create() {
			return new MyDoubleInputStream(byteIs);
		}
	}
	
	private static class MyDoubleInputStream extends AbstractDoubleIs
	{
		private final ByteIs byteIs;
		private final byte[] buffer = new byte[1000];
		
		public MyDoubleInputStream(ByteIs byteInputStream)
		{
			this.byteIs = byteInputStream;
		}

		@Override
		public void close() throws IOException
		{
			byteIs.close();
		}

		@Override
		public synchronized int readImpl(double[] array, int offset, int length)
			throws IOException
		{
			int totalElementsRead = 0;
			
			for (int i=0 ; i < length ; i++)
			{
				int cachePosition = i % buffer.length;
				
				if (cachePosition == 0)
				{
					int remaining = length - i;
					int elementsRead = byteIs.read(buffer, 0,
						buffer.length < remaining ? buffer.length : remaining);
					
					if (elementsRead > 0)
					{
						totalElementsRead += elementsRead;
					}
				}
				
				array[offset + i] = buffer[cachePosition];
			}
			
			return totalElementsRead;
		}

		@Override
		public long skip(long n) throws IOException
		{
			return byteIs.skip(n);
		}
	}
}