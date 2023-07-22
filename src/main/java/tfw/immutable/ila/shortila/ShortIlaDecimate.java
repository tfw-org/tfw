package tfw.immutable.ila.shortila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public final class ShortIlaDecimate {
    private ShortIlaDecimate() {
        // non-instantiable class
    }

    public static ShortIla create(ShortIla ila, long factor, short[] buffer) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotNull(buffer, "buffer");
        Argument.assertNotLessThan(factor, 2, "factor");
        Argument.assertNotLessThan(buffer.length, 1, "buffer.length");

        return new MyShortIla(ila, factor, buffer);
    }

    private static class MyShortIla extends AbstractShortIla {
        private final ShortIla ila;
        private final long factor;
        private final short[] buffer;

        MyShortIla(ShortIla ila, long factor, short[] buffer) {
            super((ila.length() + factor - 1) / factor);
            this.ila = ila;
            this.factor = factor;
            this.buffer = buffer;
        }

        protected void toArrayImpl(short[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            final long segmentStart = start * factor;
            final long segmentLength = StrictMath.min(ila.length() - segmentStart, length * factor - 1);
            final ShortIla segment = ShortIlaSegment.create(ila, segmentStart, segmentLength);
            final ShortIlaIterator fi = new ShortIlaIterator(segment, buffer.clone());

            for (int ii = offset; length > 0; ii += stride, --length) {
                array[ii] = fi.next();
                fi.skip(factor - 1);
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
