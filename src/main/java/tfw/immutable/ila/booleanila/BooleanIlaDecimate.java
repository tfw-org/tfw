package tfw.immutable.ila.booleanila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

/**
 *
 * @immutables.types=all
 */
public final class BooleanIlaDecimate {
    private BooleanIlaDecimate() {
        // non-instantiable class
    }

    public static BooleanIla create(BooleanIla ila, long factor) {
        return create(ila, factor, BooleanIlaIterator.DEFAULT_BUFFER_SIZE);
    }

    public static BooleanIla create(BooleanIla ila, long factor, int bufferSize) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotLessThan(factor, 2, "factor");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyBooleanIla(ila, factor, bufferSize);
    }

    private static class MyBooleanIla extends AbstractBooleanIla {
        private final BooleanIla ila;
        private final long factor;
        private final int bufferSize;

        MyBooleanIla(BooleanIla ila, long factor, int bufferSize) {
            super((ila.length() + factor - 1) / factor);
            this.ila = ila;
            this.factor = factor;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(boolean[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            final long segmentStart = start * factor;
            final long segmentLength = StrictMath.min(ila.length() - segmentStart, length * factor - 1);
            final BooleanIla segment = BooleanIlaSegment.create(ila, segmentStart, segmentLength);
            final BooleanIlaIterator fi = new BooleanIlaIterator(segment, bufferSize);

            for (int ii = offset; length > 0; ii += stride, --length) {
                array[ii] = fi.next();
                fi.skip(factor - 1);
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
