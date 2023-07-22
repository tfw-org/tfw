package tfw.immutable.ila.doubleila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public final class DoubleIlaScalarAdd {
    private DoubleIlaScalarAdd() {
        // non-instantiable class
    }

    public static DoubleIla create(DoubleIla ila, double scalar) {
        Argument.assertNotNull(ila, "ila");

        return new MyDoubleIla(ila, scalar);
    }

    private static class MyDoubleIla extends AbstractDoubleIla {
        private final DoubleIla ila;
        private final double scalar;

        MyDoubleIla(DoubleIla ila, double scalar) {
            super(ila.length());

            this.ila = ila;
            this.scalar = scalar;
        }

        protected void toArrayImpl(double[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            ila.toArray(array, offset, stride, start, length);

            for (int ii = offset; length > 0; ii += stride, --length) {
                array[ii] += scalar;
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
