package tfw.immutable.iis.byteiis;

import java.io.IOException;
import java.io.InputStream;
import tfw.check.Argument;

public final class ByteIisFromInputStream {
    private ByteIisFromInputStream() {}

    public static ByteIis create(final InputStream inputStream) {
        return new ByteIisImpl(inputStream);
    }

    private static class ByteIisImpl extends AbstractByteIis {
        private final InputStream inputStream;

        public ByteIisImpl(final InputStream inputStream) {
            Argument.assertNotNull(inputStream, "inputStream");

            this.inputStream = inputStream;
        }

        @Override
        public void closeImpl() throws IOException {
            inputStream.close();
        }

        @Override
        protected int readImpl(byte[] array, int offset, int length) throws IOException {
            return inputStream.read(array, offset, length);
        }

        @Override
        protected long skipImpl(long n) throws IOException {
            return inputStream.skip(n);
        }
    }
}
