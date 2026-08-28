package tfw.immutable.ila.floatila;

import java.io.IOException;
import tfw.check.Argument;

public final class FloatIlaFromArray {
    private FloatIlaFromArray() {
        // non-instantiable class
    }

    public static FloatIla create(float[] array) {
        Argument.assertNotNull(array, "array");

        return new FloatIlaImpl(array);
    }

    private static class FloatIlaImpl extends AbstractFloatIla {
        private final float[] array;

        private FloatIlaImpl(float[] array) {
            this.array = array;
        }

        @Override
        protected long lengthImpl() {
            return array.length;
        }

        @Override
        protected void getImpl(float[] array, int offset, long start, int length) {
            System.arraycopy(this.array, (int) start, array, offset, length);
        }

        @Override
        protected void closeImpl() throws IOException {
            // Nothing to do.
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
