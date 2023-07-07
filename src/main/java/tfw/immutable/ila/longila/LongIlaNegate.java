package tfw.immutable.ila.longila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

/**
 *
 * @immutables.types=numeric
 */
public final class LongIlaNegate {
    private LongIlaNegate() {
        // non-instantiable class
    }

    public static LongIla create(LongIla ila) {
        Argument.assertNotNull(ila, "ila");

        return new MyLongIla(ila);
    }

    private static class MyLongIla extends AbstractLongIla {
        private final LongIla ila;

        MyLongIla(LongIla ila) {
            super(ila.length());

            this.ila = ila;
        }

        protected void toArrayImpl(long[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            ila.toArray(array, offset, stride, start, length);

            for (int ii = offset; length > 0; ii += stride, --length) {
                array[ii] = -array[ii];
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
