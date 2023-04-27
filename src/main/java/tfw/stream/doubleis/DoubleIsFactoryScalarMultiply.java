package tfw.stream.doubleis;

import java.io.IOException;
import tfw.check.Argument;

public class DoubleIsFactoryScalarMultiply
{
	private DoubleIsFactoryScalarMultiply() {}
	
	public static DoubleIsFactory create(final DoubleIsFactory doubleIsFactory, final double value) {
		return new DoubleIsFactoryImpl(doubleIsFactory, value);
	}
	
	private static class DoubleIsFactoryImpl implements DoubleIsFactory {
		private final DoubleIsFactory doubleIsFactory;
		private final double value;
		
		private DoubleIsFactoryImpl(final DoubleIsFactory doubleIsFactory, final double value) {
			Argument.assertNotNull(doubleIsFactory, "doubleIsFactory");
			
			this.doubleIsFactory = doubleIsFactory;
			this.value = value;
		}

		@Override
		public DoubleIs create() {
			return new DoubleIsImpl(doubleIsFactory.create(), value);
		}
	}
	
	private static class DoubleIsImpl extends AbstractDoubleIs
	{
		private final DoubleIs doubleIs;
		private final double value;
		
		private DoubleIsImpl(final DoubleIs doubleIs, final double value)
		{
			this.doubleIs = doubleIs;
			this.value = value;
		}

		public void close() throws IOException
		{
			doubleIs.close();
		}

		public int readImpl(double[] array, int offset, int length) throws IOException
		{
			int elementsRead = doubleIs.read(array, offset, length);
			
			for (int i=0 ; i < elementsRead ; i++)
			{
				array[offset+i] *= value;
			}
			
			return elementsRead;
		}

		public long skip(long n) throws IOException
		{
			return doubleIs.skip(n);
		}
	}
}