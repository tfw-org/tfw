package tfw.immutable.ila.byteila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public final class ByteIlaScalarAdd {
    private ByteIlaScalarAdd() {
        // non-instantiable class
    }

    public static ByteIla create(ByteIla ila, byte scalar) {
        Argument.assertNotNull(ila, "ila");

        return new MyByteIla(ila, scalar);
    }

    private static class MyByteIla extends AbstractByteIla {
        private final ByteIla ila;
        private final byte scalar;

        MyByteIla(ByteIla ila, byte scalar) {
            super(ila.length());

            this.ila = ila;
            this.scalar = scalar;
        }

        protected void toArrayImpl(byte[] array, int offset, long start, int length) throws DataInvalidException {
            ila.toArray(array, offset, start, length);

            for (int ii = offset; length > 0; ii++, --length) {
                array[ii] += scalar;
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
