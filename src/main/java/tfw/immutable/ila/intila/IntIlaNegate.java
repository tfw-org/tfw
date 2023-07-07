package tfw.immutable.ila.intila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

/**
 *
 * @immutables.types=numeric
 */
public final class IntIlaNegate {
    private IntIlaNegate() {
        // non-instantiable class
    }

    public static IntIla create(IntIla ila) {
        Argument.assertNotNull(ila, "ila");

        return new MyIntIla(ila);
    }

    private static class MyIntIla extends AbstractIntIla {
        private final IntIla ila;

        MyIntIla(IntIla ila) {
            super(ila.length());

            this.ila = ila;
        }

        protected void toArrayImpl(int[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            ila.toArray(array, offset, stride, start, length);

            for (int ii = offset; length > 0; ii += stride, --length) {
                array[ii] = -array[ii];
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
