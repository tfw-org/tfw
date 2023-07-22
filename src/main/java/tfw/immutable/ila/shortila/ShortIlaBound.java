package tfw.immutable.ila.shortila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public final class ShortIlaBound {
    private ShortIlaBound() {
        // non-instantiable class
    }

    public static ShortIla create(ShortIla ila, short minimum, short maximum) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotGreaterThan(minimum, maximum, "minimum", "maximum");

        return new MyShortIla(ila, minimum, maximum);
    }

    private static class MyShortIla extends AbstractShortIla {
        private final ShortIla ila;
        private final short minimum;
        private final short maximum;

        MyShortIla(ShortIla ila, short minimum, short maximum) {
            super(ila.length());

            this.ila = ila;
            this.minimum = minimum;
            this.maximum = maximum;
        }

        protected void toArrayImpl(short[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            ila.toArray(array, offset, stride, start, length);

            for (int ii = offset; length > 0; ii += stride, --length) {
                short tmp = array[ii];
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
