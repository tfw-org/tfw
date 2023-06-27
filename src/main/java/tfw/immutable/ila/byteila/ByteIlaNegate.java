package tfw.immutable.ila.byteila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

/**
 *
 * @immutables.types=numeric
 */
public final class ByteIlaNegate {
    private ByteIlaNegate() {
        // non-instantiable class
    }

    public static ByteIla create(ByteIla ila) {
        Argument.assertNotNull(ila, "ila");

        return new MyByteIla(ila);
    }

    private static class MyByteIla extends AbstractByteIla {
        private final ByteIla ila;

        MyByteIla(ByteIla ila) {
            super(ila.length());

            this.ila = ila;
        }

        protected void toArrayImpl(byte[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            ila.toArray(array, offset, stride, start, length);

            for (int ii = offset; length > 0; ii += stride, --length) {
                array[ii] = (byte) -array[ii];
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
