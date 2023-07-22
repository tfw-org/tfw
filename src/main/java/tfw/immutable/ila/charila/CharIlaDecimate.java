package tfw.immutable.ila.charila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public final class CharIlaDecimate {
    private CharIlaDecimate() {
        // non-instantiable class
    }

    public static CharIla create(CharIla ila, long factor, char[] buffer) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotNull(buffer, "buffer");
        Argument.assertNotLessThan(factor, 2, "factor");
        Argument.assertNotLessThan(buffer.length, 1, "buffer.length");

        return new MyCharIla(ila, factor, buffer);
    }

    private static class MyCharIla extends AbstractCharIla {
        private final CharIla ila;
        private final long factor;
        private final char[] buffer;

        MyCharIla(CharIla ila, long factor, char[] buffer) {
            super((ila.length() + factor - 1) / factor);
            this.ila = ila;
            this.factor = factor;
            this.buffer = buffer;
        }

        protected void toArrayImpl(char[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            final long segmentStart = start * factor;
            final long segmentLength = StrictMath.min(ila.length() - segmentStart, length * factor - 1);
            final CharIla segment = CharIlaSegment.create(ila, segmentStart, segmentLength);
            final CharIlaIterator fi = new CharIlaIterator(segment, buffer.clone());

            for (int ii = offset; length > 0; ii += stride, --length) {
                array[ii] = fi.next();
                fi.skip(factor - 1);
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
