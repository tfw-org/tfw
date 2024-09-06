package tfw.immutable.ila.floatila;

import java.io.IOException;
import tfw.check.Argument;

public final class FloatIlaReverse {
    private FloatIlaReverse() {
        // non-instantiable class
    }

    public static FloatIla create(FloatIla ila, final float[] buffer) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotNull(buffer, "buffer");

        return new FloatIlaImpl(ila, buffer);
    }

    private static class FloatIlaImpl extends AbstractFloatIla {
        private final FloatIla ila;
        private final float[] buffer;

        private FloatIlaImpl(FloatIla ila, final float[] buffer) {
            this.ila = ila;
            this.buffer = buffer;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return ila.length();
        }

        @Override
        protected void getImpl(float[] array, int offset, long start, int length) throws IOException {
            final StridedFloatIla stridedFloatIla = StridedFloatIlaFromFloatIla.create(ila, buffer.clone());

            stridedFloatIla.get(array, offset + length - 1, -1, length() - (start + length), length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
