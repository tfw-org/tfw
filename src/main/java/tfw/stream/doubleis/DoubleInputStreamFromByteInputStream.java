package tfw.stream.doubleis;

import java.io.IOException;
import tfw.check.Argument;
import tfw.stream.byteis.ByteInputStream;

public class DoubleInputStreamFromByteInputStream {
    public static DoubleInputStream create(ByteInputStream byteInputStream) {
        Argument.assertNotNull(byteInputStream, "byteInputStream");

        return new MyDoubleInputStream(byteInputStream);
    }

    private static class MyDoubleInputStream implements DoubleInputStream {
        private final ByteInputStream byteInputStream;
        private final byte[] buffer = new byte[1000];

        public MyDoubleInputStream(ByteInputStream byteInputStream) {
            this.byteInputStream = byteInputStream;
        }

        @Override
        public long available() throws IOException {
            return byteInputStream.available();
        }

        @Override
        public void close() throws IOException {
            byteInputStream.close();
        }

        @Override
        public int read(double[] array) throws IOException {
            return read(array, 0, array.length);
        }

        @Override
        public synchronized int read(double[] array, int offset, int length) throws IOException {
            int totalElementsRead = 0;

            for (int i = 0; i < length; i++) {
                int cachePosition = i % buffer.length;

                if (cachePosition == 0) {
                    int remaining = length - i;
                    int elementsRead =
                            byteInputStream.read(buffer, 0, buffer.length < remaining ? buffer.length : remaining);

                    if (elementsRead > 0) {
                        totalElementsRead += elementsRead;
                    }
                }

                array[offset + i] = (double) buffer[cachePosition];
            }

            return totalElementsRead;
        }

        @Override
        public long skip(long n) throws IOException {
            return byteInputStream.skip(n);
        }
    }
}
