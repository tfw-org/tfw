package tfw.immutable.ila.doubleila;

import java.io.IOException;
import tfw.check.Argument;

public final class DoubleIlaSegment {
    private DoubleIlaSegment() {
        // non-instantiable class
    }

    public static DoubleIla create(DoubleIla ila, long start) {
        Argument.assertNotNull(ila, "ila");

        try {
            return create(ila, start, ila.length() - start);
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not get ila length!", e);
        }
    }

    public static DoubleIla create(DoubleIla ila, long start, long length) {
        return new DoubleIlaImpl(ila, start, length);
    }

    private static class DoubleIlaImpl extends AbstractDoubleIla {
        private final DoubleIla ila;
        private final long start;
        private final long length;

        private DoubleIlaImpl(DoubleIla ila, long start, long length) {
            Argument.assertNotNull(ila, "ila");
            Argument.assertNotLessThan(start, 0, "start");
            Argument.assertNotLessThan(length, 0, "length");
            try {
                Argument.assertNotGreaterThan(start + length, ila.length(), "start + length", "ila.length()");
            } catch (IOException e) {
                throw new IllegalArgumentException("Could not get ila length!", e);
            }

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
