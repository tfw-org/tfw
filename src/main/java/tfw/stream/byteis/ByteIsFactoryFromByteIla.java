package tfw.stream.byteis;

import java.io.IOException;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.byteila.ByteIla;

/**
 * This class is a ByteIsFactory that is backed by a ByteIla.
 */
public final class ByteIsFactoryFromByteIla {
	private ByteIsFactoryFromByteIla() {}
	
	/**
	 * Creates a ByteIsFactory that is backed by a ByteIla.
	 * 
	 * @param byteIla that backs this ByteIsFactory.
	 * @return the ByteIsFactory.
	 */
	public static ByteIsFactory create(final ByteIla byteIla) {
		return new ByteIsFactoryImpl(byteIla);
	}
	
	/**
	 * The ByteIsFactory that is backed by the ByteIla.
	 */
	private static class ByteIsFactoryImpl implements ByteIsFactory {
		/**
		 * The ByteIla that backs this ByteIsFactory.
		 */
		private final ByteIla byteIla;
		
		private ByteIsFactoryImpl(final ByteIla byteIla) {
			this.byteIla = byteIla;
		}
		
		@Override
		public ByteIs create() {
			return new ByteIsImpl(byteIla);
		}
	}
	
	/**
	 * The ByteIs implementation that is backed by a ByteIla.
	 */
	private static class ByteIsImpl extends AbstractByteIs {
		/**
		 * The ByteIla that backs this ByteIs object.
		 */
		private final ByteIla byteIla;
		
		private long index = 0;
		
		private ByteIsImpl(final ByteIla byteIla) {
			this.byteIla = byteIla;
		}

		@Override
		public void close() throws IOException {
			index = byteIla.length();
		}

		@Override
		public int readImpl(byte[] array, int offset, int length)
			throws IOException {
			if (index < byteIla.length()) {
				try {
					final int bytesToGet = (int)Math.min(byteIla.length() - index, length);
					
					byteIla.toArray(array, offset, index, bytesToGet);
					
					index += bytesToGet;
					
					return bytesToGet;
				} catch (DataInvalidException e) {
					throw new IOException("DataInvalidException thrown!", e);
				}
			}
			else {
				return -1;
			}
		}

		@Override
		public long skip(final long n) throws IOException
		{
			if (index < byteIla.length()) {
				final long originalIndex = index;
				
				index = Math.min(byteIla.length(), index + n);
				
				return index - originalIndex;
			}
			else {
				return -1;
			}
		}
	}
}