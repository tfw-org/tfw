package tfw.immutable.ila.doubleila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

/**
 *
 * @immutables.types=all
 */
public final class DoubleIlaReverse {
    private DoubleIlaReverse() {
        // non-instantiable class
    }

    public static DoubleIla create(DoubleIla ila) {
        Argument.assertNotNull(ila, "ila");

        return new MyDoubleIla(ila);
    }

    private static class MyDoubleIla extends AbstractDoubleIla {
        private final DoubleIla ila;

        MyDoubleIla(DoubleIla ila) {
            super(ila.length());
            this.ila = ila;
        }

        protected void toArrayImpl(double[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            ila.toArray(array, offset + (length - 1) * stride, -stride, length() - (start + length), length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
