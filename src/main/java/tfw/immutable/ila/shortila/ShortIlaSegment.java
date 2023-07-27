package tfw.immutable.ila.shortila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public final class ShortIlaSegment {
    private ShortIlaSegment() {
        // non-instantiable class
    }

    public static ShortIla create(ShortIla ila, long start) {
        return create(ila, start, ila.length() - start);
    }

    public static ShortIla create(ShortIla ila, long start, long length) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotLessThan(start, 0, "start");
        Argument.assertNotLessThan(length, 0, "length");
        Argument.assertNotGreaterThan(start + length, ila.length(), "start + length", "ila.length()");

        return new MyShortIla(ila, start, length);
    }

    private static class MyShortIla extends AbstractShortIla {
        private final ShortIla ila;
        private final long start;

        MyShortIla(ShortIla ila, long start, long length) {
            super(length);
            this.ila = ila;
            this.start = start;
        }

        protected void toArrayImpl(short[] array, int offset, long start, int length) throws DataInvalidException {
            ila.toArray(array, offset, this.start + start, length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
