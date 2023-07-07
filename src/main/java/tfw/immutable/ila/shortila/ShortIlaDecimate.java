package tfw.immutable.ila.shortila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

/**
 *
 * @immutables.types=all
 */
public final class ShortIlaDecimate {
    private ShortIlaDecimate() {
        // non-instantiable class
    }

    public static ShortIla create(ShortIla ila, long factor) {
        return create(ila, factor, ShortIlaIterator.DEFAULT_BUFFER_SIZE);
    }

    public static ShortIla create(ShortIla ila, long factor, int bufferSize) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotLessThan(factor, 2, "factor");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyShortIla(ila, factor, bufferSize);
    }

    private static class MyShortIla extends AbstractShortIla {
        private final ShortIla ila;
        private final long factor;
        private final int bufferSize;

        MyShortIla(ShortIla ila, long factor, int bufferSize) {
            super((ila.length() + factor - 1) / factor);
            this.ila = ila;
            this.factor = factor;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(short[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            final long segmentStart = start * factor;
            final long segmentLength = StrictMath.min(ila.length() - segmentStart, length * factor - 1);
            final ShortIla segment = ShortIlaSegment.create(ila, segmentStart, segmentLength);
            final ShortIlaIterator fi = new ShortIlaIterator(segment, bufferSize);

            for (int ii = offset; length > 0; ii += stride, --length) {
                array[ii] = fi.next();
                fi.skip(factor - 1);
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
