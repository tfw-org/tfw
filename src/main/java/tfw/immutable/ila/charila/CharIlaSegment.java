package tfw.immutable.ila.charila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public final class CharIlaSegment {
    private CharIlaSegment() {
        // non-instantiable class
    }

    public static CharIla create(CharIla ila, long start) {
        return create(ila, start, ila.length() - start);
    }

    public static CharIla create(CharIla ila, long start, long length) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotLessThan(start, 0, "start");
        Argument.assertNotLessThan(length, 0, "length");
        Argument.assertNotGreaterThan(start + length, ila.length(), "start + length", "ila.length()");

        return new MyCharIla(ila, start, length);
    }

    private static class MyCharIla extends AbstractCharIla {
        private final CharIla ila;
        private final long start;

        MyCharIla(CharIla ila, long start, long length) {
            super(length);
            this.ila = ila;
            this.start = start;
        }

        protected void toArrayImpl(char[] array, int offset, long start, int length) throws DataInvalidException {
            ila.toArray(array, offset, this.start + start, length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
