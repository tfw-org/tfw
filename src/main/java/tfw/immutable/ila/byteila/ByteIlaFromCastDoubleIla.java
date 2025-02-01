package tfw.immutable.ila.byteila;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.doubleila.DoubleIlaIterator;
import tfw.immutable.ila.doubleila.DoubleIlaSegment;

public final class ByteIlaFromCastDoubleIla {
    private ByteIlaFromCastDoubleIla() {
        // non-instantiable class
    }

    public static ByteIla create(DoubleIla doubleIla, int bufferSize) {
        return new ByteIlaImpl(doubleIla, bufferSize);
    }

    private static class ByteIlaImpl extends AbstractByteIla {
        private final DoubleIla doubleIla;
        private final int bufferSize;

        private ByteIlaImpl(DoubleIla doubleIla, int bufferSize) {
            Argument.assertNotNull(doubleIla, "doubleIla");
            Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

            this.doubleIla = doubleIla;
            this.bufferSize = bufferSize;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return doubleIla.length();
        }

        @Override
        protected void getImpl(byte[] array, int offset, long start, int length) throws IOException {
            DoubleIlaIterator fi =
                    new DoubleIlaIterator(DoubleIlaSegment.create(doubleIla, start, length), new double[bufferSize]);

            for (int ii = offset; length > 0; ii++, --length) {
                array[ii] = (byte) fi.next();
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
