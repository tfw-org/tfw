package tfw.immutable.ila.doubleila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public final class DoubleIlaBound {
    private DoubleIlaBound() {
        // non-instantiable class
    }

    public static DoubleIla create(DoubleIla ila, double minimum, double maximum) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotGreaterThan(minimum, maximum, "minimum", "maximum");

        return new MyDoubleIla(ila, minimum, maximum);
    }

    private static class MyDoubleIla extends AbstractDoubleIla {
        private final DoubleIla ila;
        private final double minimum;
        private final double maximum;

        MyDoubleIla(DoubleIla ila, double minimum, double maximum) {
            super(ila.length());

            this.ila = ila;
            this.minimum = minimum;
            this.maximum = maximum;
        }

        protected void toArrayImpl(double[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            ila.toArray(array, offset, stride, start, length);

            for (int ii = offset; length > 0; ii += stride, --length) {
                double tmp = array[ii];
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
