package tfw.immutable.ila.intila;

import java.io.IOException;
import tfw.check.Argument;

public final class IntIlaSegment {
    private IntIlaSegment() {
        // non-instantiable class
    }

    public static IntIla create(IntIla ila, long start) throws IOException {
        return create(ila, start, ila.length() - start);
    }

    public static IntIla create(IntIla ila, long start, long length) throws IOException {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotLessThan(start, 0, "start");
        Argument.assertNotLessThan(length, 0, "length");
        Argument.assertNotGreaterThan(start + length, ila.length(), "start + length", "ila.length()");

        return new MyIntIla(ila, start, length);
    }

    private static class MyIntIla extends AbstractIntIla {
        private final IntIla ila;
        private final long start;
        private final long length;

        MyIntIla(IntIla ila, long start, long length) {
            this.ila = ila;
            this.start = start;
            this.length = length;
        }

        @Override
        protected long lengthImpl() {
            return length;
        }

        @Override
        protected void getImpl(int[] array, int offset, long start, int length) throws IOException {
            ila.get(array, offset, this.start + start, length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
