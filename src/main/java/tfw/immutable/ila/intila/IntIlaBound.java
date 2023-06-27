package tfw.immutable.ila.intila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

/**
 *
 * @immutables.types=numeric
 */
public final class IntIlaBound {
    private IntIlaBound() {
        // non-instantiable class
    }

    public static IntIla create(IntIla ila, int minimum, int maximum) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotGreaterThan(minimum, maximum, "minimum", "maximum");

        return new MyIntIla(ila, minimum, maximum);
    }

    private static class MyIntIla extends AbstractIntIla {
        private final IntIla ila;
        private final int minimum;
        private final int maximum;

        MyIntIla(IntIla ila, int minimum, int maximum) {
            super(ila.length());

            this.ila = ila;
            this.minimum = minimum;
            this.maximum = maximum;
        }

        protected void toArrayImpl(int[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            ila.toArray(array, offset, stride, start, length);

            for (int ii = offset; length > 0; ii += stride, --length) {
                int tmp = array[ii];
                if (tmp < minimum) {
                    array[ii] = minimum;
                } else if (tmp > maximum) {
                    array[ii] = maximum;
                }
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
