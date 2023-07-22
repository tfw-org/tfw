package tfw.immutable.ila.longila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public final class LongIlaScalarMultiply {
    private LongIlaScalarMultiply() {
        // non-instantiable class
    }

    public static LongIla create(LongIla ila, long scalar) {
        Argument.assertNotNull(ila, "ila");

        return new MyLongIla(ila, scalar);
    }

    private static class MyLongIla extends AbstractLongIla {
        private final LongIla ila;
        private final long scalar;

        MyLongIla(LongIla ila, long scalar) {
            super(ila.length());

            this.ila = ila;
            this.scalar = scalar;
        }

        protected void toArrayImpl(long[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            ila.toArray(array, offset, stride, start, length);

            for (int ii = offset; length > 0; ii += stride, --length) {
                array[ii] *= scalar;
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
