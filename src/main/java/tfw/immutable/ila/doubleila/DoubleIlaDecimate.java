package tfw.immutable.ila.doubleila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public final class DoubleIlaDecimate {
    private DoubleIlaDecimate() {
        // non-instantiable class
    }

    public static DoubleIla create(DoubleIla ila, long factor, double[] buffer) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotNull(buffer, "buffer");
        Argument.assertNotLessThan(factor, 2, "factor");
        Argument.assertNotLessThan(buffer.length, 1, "buffer.length");

        return new MyDoubleIla(ila, factor, buffer);
    }

    private static class MyDoubleIla extends AbstractDoubleIla {
        private final DoubleIla ila;
        private final long factor;
        private final double[] buffer;

        MyDoubleIla(DoubleIla ila, long factor, double[] buffer) {
            super((ila.length() + factor - 1) / factor);
            this.ila = ila;
            this.factor = factor;
            this.buffer = buffer;
        }

        protected void toArrayImpl(double[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            final long segmentStart = start * factor;
            final long segmentLength = StrictMath.min(ila.length() - segmentStart, length * factor - 1);
            final DoubleIla segment = DoubleIlaSegment.create(ila, segmentStart, segmentLength);
            final DoubleIlaIterator fi = new DoubleIlaIterator(segment, buffer.clone());

            for (int ii = offset; length > 0; ii += stride, --length) {
                array[ii] = fi.next();
                fi.skip(factor - 1);
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
