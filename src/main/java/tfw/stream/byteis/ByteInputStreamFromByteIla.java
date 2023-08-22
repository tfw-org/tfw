package tfw.stream.byteis;

import java.io.IOException;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.byteila.ByteIla;

public final class ByteInputStreamFromByteIla {
    public static ByteInputStream create(final ByteIla byteIla) {
        return new MyByteInputStream(byteIla);
    }

    private static class MyByteInputStream implements ByteInputStream {
        private final ByteIla byteIla;
        private final byte[] buffer = new byte[1];

        private long index = 0;

        public MyByteInputStream(final ByteIla byteIla) {
            this.byteIla = byteIla;
        }

        @Override
        public long available() throws DataInvalidException {
            return byteIla.length() - index;
        }

        @Override
        public void close() throws DataInvalidException {
            index = byteIla.length();
        }

        @Override
        public int read(byte[] array) throws DataInvalidException {
            return read(array, 0, array.length);
        }

        @Override
        public int read(byte[] array, int offset, int length) throws DataInvalidException {
            if (array == null) {
                throw new NullPointerException("array cannot be null");
            } else if (offset < 0 || length < 0 || length > array.length - offset) {
                throw new IndexOutOfBoundsException();
            } else if (length == 0) {
                return 0;
            }

            if (index < byteIla.length()) {
                try {
                    byteIla.toArray(buffer, 0, index++, 1);
                } catch (IOException e) {
                    throw new DataInvalidException("Could not get data!", e);
                }

                array[offset] = buffer[0];
            } else {
                return -1;
            }

            int i = 1;
            try {
                for (; i < length; i++) {
                    if (index < byteIla.length()) {
                        byteIla.toArray(buffer, 0, index++, 1);

                        array[offset + i] = buffer[0];
                    } else {
                        break;
                    }
                }
            } catch (Exception ee) {
            }

            return 0;
        }

        @Override
        public long skip(long n) throws DataInvalidException {
            final long originalIndex = index;

            index = Math.min(byteIla.length(), index + n);

            return index - originalIndex;
        }
    }
}
