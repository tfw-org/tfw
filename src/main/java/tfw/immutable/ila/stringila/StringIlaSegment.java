package tfw.immutable.ila.stringila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

/**
 *
 * @immutables.types=all
 */
public final class StringIlaSegment {
    private StringIlaSegment() {
        // non-instantiable class
    }

    public static StringIla create(StringIla ila, long start) {
        return create(ila, start, ila.length() - start);
    }

    public static StringIla create(StringIla ila, long start, long length) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotLessThan(start, 0, "start");
        Argument.assertNotLessThan(length, 0, "length");
        Argument.assertNotGreaterThan(start + length, ila.length(), "start + length", "ila.length()");

        return new MyStringIla(ila, start, length);
    }

    private static class MyStringIla extends AbstractStringIla {
        private final StringIla ila;
        private final long start;

        MyStringIla(StringIla ila, long start, long length) {
            super(length);
            this.ila = ila;
            this.start = start;
        }

        protected void toArrayImpl(String[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            ila.toArray(array, offset, stride, this.start + start, length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
