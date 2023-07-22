package tfw.immutable.ila.shortila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public final class ShortIlaNegate {
    private ShortIlaNegate() {
        // non-instantiable class
    }

    public static ShortIla create(ShortIla ila) {
        Argument.assertNotNull(ila, "ila");

        return new MyShortIla(ila);
    }

    private static class MyShortIla extends AbstractShortIla {
        private final ShortIla ila;

        MyShortIla(ShortIla ila) {
            super(ila.length());

            this.ila = ila;
        }

        protected void toArrayImpl(short[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            ila.toArray(array, offset, stride, start, length);

            for (int ii = offset; length > 0; ii += stride, --length) {
                array[ii] = (short) -array[ii];
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
