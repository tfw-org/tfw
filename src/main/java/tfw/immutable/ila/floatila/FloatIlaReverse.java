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

        return new MyFloatIla(ila, buffer);
    }

    private static class MyFloatIla extends AbstractFloatIla {
        private final FloatIla ila;
        private final float[] buffer;

        MyFloatIla(FloatIla ila, final float[] buffer) {
            super(ila.length());

            this.ila = ila;
            this.buffer = buffer;
        }

        protected void toArrayImpl(float[] array, int offset, long start, int length) throws IOException {
            final StridedFloatIla stridedFloatIla = new StridedFloatIla(ila, buffer.clone());

            stridedFloatIla.toArray(array, offset + (length - 1), -1, length() - (start + length), length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
