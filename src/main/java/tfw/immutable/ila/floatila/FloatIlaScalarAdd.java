package tfw.immutable.ila.floatila;

import java.io.IOException;
import tfw.check.Argument;

public final class FloatIlaScalarAdd {
    private FloatIlaScalarAdd() {
        // non-instantiable class
    }

    public static FloatIla create(FloatIla ila, float scalar) {
        Argument.assertNotNull(ila, "ila");

        return new FloatIlaImpl(ila, scalar);
    }

    private static class FloatIlaImpl extends AbstractFloatIla {
        private final FloatIla ila;
        private final float scalar;

        private FloatIlaImpl(FloatIla ila, float scalar) {
            this.ila = ila;
            this.scalar = scalar;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return ila.length();
        }

        @Override
        protected void getImpl(float[] array, int offset, long start, int length) throws IOException {
            ila.get(array, offset, start, length);

            for (int ii = offset; length > 0; ii++, --length) {
                array[ii] += scalar;
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
