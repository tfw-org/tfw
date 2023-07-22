package tfw.immutable.ila.floatila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public final class FloatIlaScalarAdd {
    private FloatIlaScalarAdd() {
        // non-instantiable class
    }

    public static FloatIla create(FloatIla ila, float scalar) {
        Argument.assertNotNull(ila, "ila");

        return new MyFloatIla(ila, scalar);
    }

    private static class MyFloatIla extends AbstractFloatIla {
        private final FloatIla ila;
        private final float scalar;

        MyFloatIla(FloatIla ila, float scalar) {
            super(ila.length());

            this.ila = ila;
            this.scalar = scalar;
        }

        protected void toArrayImpl(float[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            ila.toArray(array, offset, stride, start, length);

            for (int ii = offset; length > 0; ii += stride, --length) {
                array[ii] += scalar;
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
