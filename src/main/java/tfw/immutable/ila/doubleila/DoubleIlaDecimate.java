package tfw.immutable.ila.doubleila;

import java.io.IOException;
import tfw.check.Argument;

public final class DoubleIlaDecimate {
    private DoubleIlaDecimate() {
        // non-instantiable class
    }

    public static DoubleIla create(DoubleIla ila, long factor, double[] buffer) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotNull(buffer, "buffer");
        Argument.assertNotLessThan(factor, 2, "factor");
        Argument.assertNotLessThan(buffer.length, 1, "buffer.length");

        return new DoubleIlaImpl(ila, factor, buffer);
    }

    private static class DoubleIlaImpl extends AbstractDoubleIla {
        private final DoubleIla ila;
        private final long factor;
        private final double[] buffer;

        private DoubleIlaImpl(DoubleIla ila, long factor, double[] buffer) {
            this.ila = ila;
            this.factor = factor;
            this.buffer = buffer;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return (ila.length() + factor - 1) / factor;
        }

        @Override
        protected void getImpl(double[] array, int offset, long start, int length) throws IOException {
            final long segmentStart = start * factor;
            final long segmentLength = StrictMath.min(ila.length() - segmentStart, length * factor - 1);
            final DoubleIla segment = DoubleIlaSegment.create(ila, segmentStart, segmentLength);
            final DoubleIlaIterator fi = new DoubleIlaIterator(segment, buffer.clone());

            for (int ii = offset; length > 0; ii++, --length) {
                array[ii] = fi.next();
                fi.skip(factor - 1);
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
