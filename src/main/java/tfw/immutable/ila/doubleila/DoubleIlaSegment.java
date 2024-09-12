package tfw.immutable.ila.doubleila;

import java.io.IOException;
import tfw.check.Argument;

public final class DoubleIlaSegment {
    private DoubleIlaSegment() {
        // non-instantiable class
    }

    public static DoubleIla create(DoubleIla ila, long start) throws IOException {
        Argument.assertNotNull(ila, "ila");

        return create(ila, start, ila.length() - start);
    }

    public static DoubleIla create(DoubleIla ila, long start, long length) throws IOException {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotLessThan(start, 0, "start");
        Argument.assertNotLessThan(length, 0, "length");
        Argument.assertNotGreaterThan(start + length, ila.length(), "start + length", "ila.length()");

        return new DoubleIlaImpl(ila, start, length);
    }

    private static class DoubleIlaImpl extends AbstractDoubleIla {
        private final DoubleIla ila;
        private final long start;
        private final long length;

        private DoubleIlaImpl(DoubleIla ila, long start, long length) {
            this.ila = ila;
            this.start = start;
            this.length = length;
        }

        @Override
        protected long lengthImpl() {
            return length;
        }

        @Override
        protected void getImpl(double[] array, int offset, long start, int length) throws IOException {
            ila.get(array, offset, this.start + start, length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
