package tfw.immutable.ila.byteila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public final class ByteIlaBound {
    private ByteIlaBound() {
        // non-instantiable class
    }

    public static ByteIla create(ByteIla ila, byte minimum, byte maximum) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotGreaterThan(minimum, maximum, "minimum", "maximum");

        return new MyByteIla(ila, minimum, maximum);
    }

    private static class MyByteIla extends AbstractByteIla {
        private final ByteIla ila;
        private final byte minimum;
        private final byte maximum;

        MyByteIla(ByteIla ila, byte minimum, byte maximum) {
            super(ila.length());

            this.ila = ila;
            this.minimum = minimum;
            this.maximum = maximum;
        }

        protected void toArrayImpl(byte[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            ila.toArray(array, offset, stride, start, length);

            for (int ii = offset; length > 0; ii += stride, --length) {
                byte tmp = array[ii];
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
