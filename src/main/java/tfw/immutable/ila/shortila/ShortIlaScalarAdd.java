package tfw.immutable.ila.shortila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public final class ShortIlaScalarAdd {
    private ShortIlaScalarAdd() {
        // non-instantiable class
    }

    public static ShortIla create(ShortIla ila, short scalar) {
        Argument.assertNotNull(ila, "ila");

        return new MyShortIla(ila, scalar);
    }

    private static class MyShortIla extends AbstractShortIla {
        private final ShortIla ila;
        private final short scalar;

        MyShortIla(ShortIla ila, short scalar) {
            super(ila.length());

            this.ila = ila;
            this.scalar = scalar;
        }

        protected void toArrayImpl(short[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            ila.toArray(array, offset, stride, start, length);

            for (int ii = offset; length > 0; ii += stride, --length) {
                array[ii] += scalar;
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
