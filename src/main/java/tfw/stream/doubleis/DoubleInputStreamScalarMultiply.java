package tfw.stream.doubleis;

import java.io.IOException;
import tfw.check.Argument;

public class DoubleInputStreamScalarMultiply {
    public static DoubleInputStream create(DoubleInputStream doubleInputStream, double value) {
        Argument.assertNotNull(doubleInputStream, "doubleInputStream");

        return new DoubleInputStreamImpl(doubleInputStream, value);
    }

    private static class DoubleInputStreamImpl implements DoubleInputStream {
        private final DoubleInputStream doubleInputStream;
        private final double value;

        private DoubleInputStreamImpl(DoubleInputStream doubleInputStream, double value) {
            this.doubleInputStream = doubleInputStream;
            this.value = value;
        }

        public long available() throws IOException {
            return doubleInputStream.available();
        }

        public void close() throws IOException {
            doubleInputStream.close();
        }

        public int read(double[] array) throws IOException {
            return read(array, 0, array.length);
        }

        public int read(double[] array, int offset, int length) throws IOException {
            int elementsRead = doubleInputStream.read(array, offset, length);

            for (int i = 0; i < elementsRead; i++) {
                array[offset + i] *= value;
            }

            return elementsRead;
        }

        public long skip(long n) throws IOException {
            return doubleInputStream.skip(n);
        }
    }
}
