package tfw.immutable.ila.shortila;

import java.io.IOException;
import tfw.check.Argument;

public final class ShortIlaSegment {
    private ShortIlaSegment() {
        // non-instantiable class
    }

    public static ShortIla create(ShortIla ila, long start) throws IOException {
        return create(ila, start, ila.length() - start);
    }

    public static ShortIla create(ShortIla ila, long start, long length) throws IOException {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotLessThan(start, 0, "start");
        Argument.assertNotLessThan(length, 0, "length");
        Argument.assertNotGreaterThan(start + length, ila.length(), "start + length", "ila.length()");

        return new MyShortIla(ila, start, length);
    }

    private static class MyShortIla extends AbstractShortIla {
        private final ShortIla ila;
        private final long start;
        private final long length;

        MyShortIla(ShortIla ila, long start, long length) {
            this.ila = ila;
            this.start = start;
            this.length = length;
        }

        @Override
        protected long lengthImpl() {
            return length;
        }

        @Override
        protected void toArrayImpl(short[] array, int offset, long start, int length) throws IOException {
            ila.toArray(array, offset, this.start + start, length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
