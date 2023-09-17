package tfw.immutable.ila.longila;

import java.io.IOException;
import tfw.check.Argument;

public final class LongIlaSegment {
    private LongIlaSegment() {
        // non-instantiable class
    }

    public static LongIla create(LongIla ila, long start) throws IOException {
        return create(ila, start, ila.length() - start);
    }

    public static LongIla create(LongIla ila, long start, long length) throws IOException {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotLessThan(start, 0, "start");
        Argument.assertNotLessThan(length, 0, "length");
        Argument.assertNotGreaterThan(start + length, ila.length(), "start + length", "ila.length()");

        return new LongIlaImpl(ila, start, length);
    }

    private static class LongIlaImpl extends AbstractLongIla {
        private final LongIla ila;
        private final long start;
        private final long length;

        private LongIlaImpl(LongIla ila, long start, long length) {
            this.ila = ila;
            this.start = start;
            this.length = length;
        }

        @Override
        protected long lengthImpl() {
            return length;
        }

        @Override
        protected void getImpl(long[] array, int offset, long start, int length) throws IOException {
            ila.get(array, offset, this.start + start, length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
