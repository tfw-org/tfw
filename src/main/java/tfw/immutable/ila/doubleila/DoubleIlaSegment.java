package tfw.immutable.ila.doubleila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public final class DoubleIlaSegment {
    private DoubleIlaSegment() {
        // non-instantiable class
    }

    public static DoubleIla create(DoubleIla ila, long start) {
        return create(ila, start, ila.length() - start);
    }

    public static DoubleIla create(DoubleIla ila, long start, long length) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotLessThan(start, 0, "start");
        Argument.assertNotLessThan(length, 0, "length");
        Argument.assertNotGreaterThan(start + length, ila.length(), "start + length", "ila.length()");

        return new MyDoubleIla(ila, start, length);
    }

    private static class MyDoubleIla extends AbstractDoubleIla {
        private final DoubleIla ila;
        private final long start;

        MyDoubleIla(DoubleIla ila, long start, long length) {
            super(length);
            this.ila = ila;
            this.start = start;
        }

        protected void toArrayImpl(double[] array, int offset, long start, int length) throws DataInvalidException {
            ila.toArray(array, offset, this.start + start, length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
