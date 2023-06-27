package tfw.immutable.ila.intila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

/**
 *
 * @immutables.types=numeric
 */
public final class IntIlaScalarMultiply {
    private IntIlaScalarMultiply() {
        // non-instantiable class
    }

    public static IntIla create(IntIla ila, int scalar) {
        Argument.assertNotNull(ila, "ila");

        return new MyIntIla(ila, scalar);
    }

    private static class MyIntIla extends AbstractIntIla {
        private final IntIla ila;
        private final int scalar;

        MyIntIla(IntIla ila, int scalar) {
            super(ila.length());

            this.ila = ila;
            this.scalar = scalar;
        }

        protected void toArrayImpl(int[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            ila.toArray(array, offset, stride, start, length);

            for (int ii = offset; length > 0; ii += stride, --length) {
                array[ii] *= scalar;
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
