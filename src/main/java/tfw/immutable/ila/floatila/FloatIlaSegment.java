package tfw.immutable.ila.floatila;

import java.io.IOException;
import tfw.check.Argument;

public final class FloatIlaSegment {
    private FloatIlaSegment() {
        // non-instantiable class
    }

    public static FloatIla create(FloatIla ila, long start) throws IOException {
        return create(ila, start, ila.length() - start);
    }

    public static FloatIla create(FloatIla ila, long start, long length) throws IOException {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotLessThan(start, 0, "start");
        Argument.assertNotLessThan(length, 0, "length");
        Argument.assertNotGreaterThan(start + length, ila.length(), "start + length", "ila.length()");

        return new FloatIlaImpl(ila, start, length);
    }

    private static class FloatIlaImpl extends AbstractFloatIla {
        private final FloatIla ila;
        private final long start;
        private final long length;

        private FloatIlaImpl(FloatIla ila, long start, long length) {
            this.ila = ila;
            this.start = start;
            this.length = length;
        }

        @Override
        protected long lengthImpl() {
            return length;
        }

        @Override
        protected void getImpl(float[] array, int offset, long start, int length) throws IOException {
            ila.get(array, offset, this.start + start, length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
