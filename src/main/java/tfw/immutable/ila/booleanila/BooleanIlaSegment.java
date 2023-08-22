package tfw.immutable.ila.booleanila;

import java.io.IOException;
import tfw.check.Argument;

public final class BooleanIlaSegment {
    private BooleanIlaSegment() {
        // non-instantiable class
    }

    public static BooleanIla create(BooleanIla ila, long start) {
        return create(ila, start, ila.length() - start);
    }

    public static BooleanIla create(BooleanIla ila, long start, long length) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotLessThan(start, 0, "start");
        Argument.assertNotLessThan(length, 0, "length");
        Argument.assertNotGreaterThan(start + length, ila.length(), "start + length", "ila.length()");

        return new MyBooleanIla(ila, start, length);
    }

    private static class MyBooleanIla extends AbstractBooleanIla {
        private final BooleanIla ila;
        private final long start;

        MyBooleanIla(BooleanIla ila, long start, long length) {
            super(length);
            this.ila = ila;
            this.start = start;
        }

        protected void toArrayImpl(boolean[] array, int offset, long start, int length) throws IOException {
            ila.toArray(array, offset, this.start + start, length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
