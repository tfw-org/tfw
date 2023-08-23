package tfw.immutable.ila.booleanila;

import java.io.IOException;
import tfw.check.Argument;

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
            this.ila = ila;
            this.factor = factor;
            this.buffer = buffer;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return (ila.length() + factor - 1) / factor;
        }

        @Override
        protected void toArrayImpl(boolean[] array, int offset, long start, int length) throws IOException {
            final long segmentStart = start * factor;
            final long segmentLength = StrictMath.min(ila.length() - segmentStart, length * factor - 1);
            final BooleanIla segment = BooleanIlaSegment.create(ila, segmentStart, segmentLength);
            final BooleanIlaIterator fi = new BooleanIlaIterator(segment, buffer.clone());

            for (int ii = offset; length > 0; ii++, --length) {
                array[ii] = fi.next();
                fi.skip(factor - 1);
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
