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

    public static LongIla create(LongIla ila, long factor) {
        return create(ila, factor, LongIlaIterator.DEFAULT_BUFFER_SIZE);
    }

    public static LongIla create(LongIla ila, long factor, int bufferSize) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotLessThan(factor, 2, "factor");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyLongIla(ila, factor, bufferSize);
    }

    private static class MyLongIla extends AbstractLongIla {
        private final LongIla ila;
        private final long factor;
        private final int bufferSize;

        MyLongIla(LongIla ila, long factor, int bufferSize) {
            super((ila.length() + factor - 1) / factor);
            this.ila = ila;
            this.factor = factor;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(long[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            final long segmentStart = start * factor;
            final long segmentLength = StrictMath.min(ila.length() - segmentStart, length * factor - 1);
            final LongIla segment = LongIlaSegment.create(ila, segmentStart, segmentLength);
            final LongIlaIterator fi = new LongIlaIterator(segment, bufferSize);

            for (int ii = offset; length > 0; ii += stride, --length) {
                array[ii] = fi.next();
                fi.skip(factor - 1);
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
