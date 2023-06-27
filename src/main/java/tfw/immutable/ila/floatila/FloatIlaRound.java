package tfw.immutable.ila.floatila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

/**
 *
 * @immutables.types=floating
 */
public final class FloatIlaRound {
    private FloatIlaRound() {
        // non-instantiable class
    }

    public static FloatIla create(FloatIla ila) {
        Argument.assertNotNull(ila, "ila");

        return new MyFloatIla(ila);
    }

    private static class MyFloatIla extends AbstractFloatIla {
        private final FloatIla ila;

        MyFloatIla(FloatIla ila) {
            super(ila.length());

            this.ila = ila;
        }

        protected void toArrayImpl(float[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            ila.toArray(array, offset, stride, start, length);

            for (int ii = offset; length > 0; ii += stride, --length) {
                array[ii] = (float) StrictMath.rint(array[ii]);
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
