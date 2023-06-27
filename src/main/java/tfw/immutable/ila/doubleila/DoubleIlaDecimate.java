package tfw.immutable.ila.doubleila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

/**
 *
 * @immutables.types=all
 */
public final class DoubleIlaDecimate {
    private DoubleIlaDecimate() {
        // non-instantiable class
    }

    public static DoubleIla create(DoubleIla ila, long factor) {
        return create(ila, factor, DoubleIlaIterator.DEFAULT_BUFFER_SIZE);
    }

    public static DoubleIla create(DoubleIla ila, long factor, int bufferSize) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotLessThan(factor, 2, "factor");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyDoubleIla(ila, factor, bufferSize);
    }

    private static class MyDoubleIla extends AbstractDoubleIla {
        private final DoubleIla ila;
        private final long factor;
        private final int bufferSize;

        MyDoubleIla(DoubleIla ila, long factor, int bufferSize) {
            super((ila.length() + factor - 1) / factor);
            this.ila = ila;
            this.factor = factor;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(double[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            final long segmentStart = start * factor;
            final long segmentLength = StrictMath.min(ila.length() - segmentStart, length * factor - 1);
            final DoubleIla segment = DoubleIlaSegment.create(ila, segmentStart, segmentLength);
            final DoubleIlaIterator fi = new DoubleIlaIterator(segment, bufferSize);

            for (int ii = offset; length > 0; ii += stride, --length) {
                array[ii] = (double) fi.next();
                fi.skip(factor - 1);
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
