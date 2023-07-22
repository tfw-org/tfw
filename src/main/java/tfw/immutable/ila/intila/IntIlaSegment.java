package tfw.immutable.ila.intila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public final class IntIlaSegment {
    private IntIlaSegment() {
        // non-instantiable class
    }

    public static IntIla create(IntIla ila, long start) {
        return create(ila, start, ila.length() - start);
    }

    public static IntIla create(IntIla ila, long start, long length) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotLessThan(start, 0, "start");
        Argument.assertNotLessThan(length, 0, "length");
        Argument.assertNotGreaterThan(start + length, ila.length(), "start + length", "ila.length()");

        return new MyIntIla(ila, start, length);
    }

    private static class MyIntIla extends AbstractIntIla {
        private final IntIla ila;
        private final long start;

        MyIntIla(IntIla ila, long start, long length) {
            super(length);
            this.ila = ila;
            this.start = start;
        }

        protected void toArrayImpl(int[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            ila.toArray(array, offset, stride, this.start + start, length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
