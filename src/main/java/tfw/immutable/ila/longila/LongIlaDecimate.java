package tfw.immutable.ila.longila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

/**
 *
 * @immutables.types=all
 */
public final class LongIlaDecimate {
    private LongIlaDecimate() {
        // non-instantiable class
    }

    public static LongIla create(LongIla ila, long factor, long[] buffer) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotNull(buffer, "buffer");
        Argument.assertNotLessThan(factor, 2, "factor");
        Argument.assertNotLessThan(buffer.length, 1, "buffer.length");

        return new MyLongIla(ila, factor, buffer);
    }

    private static class MyLongIla extends AbstractLongIla {
        private final LongIla ila;
        private final long factor;
        private final long[] buffer;

        MyLongIla(LongIla ila, long factor, long[] buffer) {
            super((ila.length() + factor - 1) / factor);
            this.ila = ila;
            this.factor = factor;
            this.buffer = buffer;
        }

        protected void toArrayImpl(long[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            final long segmentStart = start * factor;
            final long segmentLength = StrictMath.min(ila.length() - segmentStart, length * factor - 1);
            final LongIla segment = LongIlaSegment.create(ila, segmentStart, segmentLength);
            final LongIlaIterator fi = new LongIlaIterator(segment, buffer.clone());

            for (int ii = offset; length > 0; ii += stride, --length) {
                array[ii] = fi.next();
                fi.skip(factor - 1);
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
