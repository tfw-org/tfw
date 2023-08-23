package tfw.immutable.ila.longila;

import java.io.IOException;
import tfw.check.Argument;

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
            this.ila = ila;
            this.factor = factor;
            this.buffer = buffer;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return (ila.length() + factor - 1) / factor;
        }

        @Override
        protected void toArrayImpl(long[] array, int offset, long start, int length) throws IOException {
            final long segmentStart = start * factor;
            final long segmentLength = StrictMath.min(ila.length() - segmentStart, length * factor - 1);
            final LongIla segment = LongIlaSegment.create(ila, segmentStart, segmentLength);
            final LongIlaIterator fi = new LongIlaIterator(segment, buffer.clone());

            for (int ii = offset; length > 0; ii++, --length) {
                array[ii] = fi.next();
                fi.skip(factor - 1);
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
