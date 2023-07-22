package tfw.immutable.ila.longila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public final class LongIlaSegment {
    private LongIlaSegment() {
        // non-instantiable class
    }

    public static LongIla create(LongIla ila, long start) {
        return create(ila, start, ila.length() - start);
    }

    public static LongIla create(LongIla ila, long start, long length) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotLessThan(start, 0, "start");
        Argument.assertNotLessThan(length, 0, "length");
        Argument.assertNotGreaterThan(start + length, ila.length(), "start + length", "ila.length()");

        return new MyLongIla(ila, start, length);
    }

    private static class MyLongIla extends AbstractLongIla {
        private final LongIla ila;
        private final long start;

        MyLongIla(LongIla ila, long start, long length) {
            super(length);
            this.ila = ila;
            this.start = start;
        }

        protected void toArrayImpl(long[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            ila.toArray(array, offset, stride, this.start + start, length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
