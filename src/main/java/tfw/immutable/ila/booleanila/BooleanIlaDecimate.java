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

    public static BooleanIla create(BooleanIla ila, long factor, boolean[] buffer) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotNull(buffer, "buffer");
        Argument.assertNotLessThan(factor, 2, "factor");
        Argument.assertNotLessThan(buffer.length, 1, "buffer.length");

        return new MyBooleanIla(ila, factor, buffer);
    }

    private static class MyBooleanIla extends AbstractBooleanIla {
        private final BooleanIla ila;
        private final long factor;
        private final boolean[] buffer;

        MyBooleanIla(BooleanIla ila, long factor, boolean[] buffer) {
            super((ila.length() + factor - 1) / factor);
            this.ila = ila;
            this.factor = factor;
            this.buffer = buffer;
        }

        protected void toArrayImpl(boolean[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            final long segmentStart = start * factor;
            final long segmentLength = StrictMath.min(ila.length() - segmentStart, length * factor - 1);
            final BooleanIla segment = BooleanIlaSegment.create(ila, segmentStart, segmentLength);
            final BooleanIlaIterator fi = new BooleanIlaIterator(segment, buffer.clone());

            for (int ii = offset; length > 0; ii += stride, --length) {
                array[ii] = fi.next();
                fi.skip(factor - 1);
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
