package tfw.stream.byteis;

import java.io.IOException;
import java.io.InputStream;
import tfw.check.Argument;

public final class ByteIsFactoryFromInputStreamFactory {
	private ByteIsFactoryFromInputStreamFactory() {}
	
	public static ByteIsFactory create(final InputStreamFactory inputStreamFactory) {
		return new ByteInputStreamFactoryImpl(inputStreamFactory);
	}
	
	private static class ByteInputStreamFactoryImpl implements ByteIsFactory {
		private final InputStreamFactory inputStreamFactory;
		
		private ByteInputStreamFactoryImpl(final InputStreamFactory inputStreamFactory) {
			Argument.assertNotNull(inputStreamFactory, "inputStreamFactory");
			
			this.inputStreamFactory = inputStreamFactory;
		}
		
		@Override
		public ByteIs create() {
			return new ByteInputStreamImpl(inputStreamFactory.create());
		}
	}
	
	private static class ByteInputStreamImpl implements ByteIs {
		private final InputStream inputStream;
		
		public ByteInputStreamImpl(final InputStream inputStream) {
			this.inputStream = inputStream;
		}

		@Override
		public void close() throws IOException {
			inputStream.close();
		}

		@Override
		public int read(byte[] array, int offset, int length) throws IOException {
			return inputStream.read(array, offset, length);
		}

		@Override
		public long skip(long n) throws IOException {
			return inputStream.skip(n);
		}
	}
}
