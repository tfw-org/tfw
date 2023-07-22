package tfw.immutable.ila.longila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public final class LongIlaBound {
    private LongIlaBound() {
        // non-instantiable class
    }

    public static LongIla create(LongIla ila, long minimum, long maximum) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotGreaterThan(minimum, maximum, "minimum", "maximum");

        return new MyLongIla(ila, minimum, maximum);
    }

    private static class MyLongIla extends AbstractLongIla {
        private final LongIla ila;
        private final long minimum;
        private final long maximum;

        MyLongIla(LongIla ila, long minimum, long maximum) {
            super(ila.length());

            this.ila = ila;
            this.minimum = minimum;
            this.maximum = maximum;
        }

        protected void toArrayImpl(long[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            ila.toArray(array, offset, stride, start, length);

            for (int ii = offset; length > 0; ii += stride, --length) {
                long tmp = array[ii];
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
