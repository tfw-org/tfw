package tfw.immutable.ila.charila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

/**
 *
 * @immutables.types=all
 */
public final class CharIlaDecimate {
    private CharIlaDecimate() {
        // non-instantiable class
    }

    public static CharIla create(CharIla ila, long factor) {
        return create(ila, factor, CharIlaIterator.DEFAULT_BUFFER_SIZE);
    }

    public static CharIla create(CharIla ila, long factor, int bufferSize) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotLessThan(factor, 2, "factor");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyCharIla(ila, factor, bufferSize);
    }

    private static class MyCharIla extends AbstractCharIla {
        private final CharIla ila;
        private final long factor;
        private final int bufferSize;

        MyCharIla(CharIla ila, long factor, int bufferSize) {
            super((ila.length() + factor - 1) / factor);
            this.ila = ila;
            this.factor = factor;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(char[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            final long segmentStart = start * factor;
            final long segmentLength = StrictMath.min(ila.length() - segmentStart, length * factor - 1);
            final CharIla segment = CharIlaSegment.create(ila, segmentStart, segmentLength);
            final CharIlaIterator fi = new CharIlaIterator(segment, bufferSize);

            for (int ii = offset; length > 0; ii += stride, --length) {
                array[ii] = fi.next();
                fi.skip(factor - 1);
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
