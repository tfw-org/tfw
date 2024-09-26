package tfw.immutable.ila.doubleila;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaIterator;
import tfw.immutable.ila.byteila.ByteIlaSegment;

public final class DoubleIlaFromCastByteIla {
    private DoubleIlaFromCastByteIla() {
        // non-instantiable class
    }

    public static DoubleIla create(ByteIla byteIla, int bufferSize) {
        Argument.assertNotNull(byteIla, "byteIla");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new DoubleIlaImpl(byteIla, bufferSize);
    }

    private static class DoubleIlaImpl extends AbstractDoubleIla {
        private final ByteIla byteIla;
        private final int bufferSize;

        private DoubleIlaImpl(ByteIla byteIla, int bufferSize) {
            this.byteIla = byteIla;
            this.bufferSize = bufferSize;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return byteIla.length();
        }

        @Override
        protected void getImpl(double[] array, int offset, long start, int length) throws IOException {
            ByteIlaIterator fi =
                    new ByteIlaIterator(ByteIlaSegment.create(byteIla, start, length), new byte[bufferSize]);

            for (int ii = offset; length > 0; ii++, --length) {
                array[ii] = (double) fi.next();
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
