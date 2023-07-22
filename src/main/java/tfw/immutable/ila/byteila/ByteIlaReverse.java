package tfw.immutable.ila.byteila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public final class ByteIlaReverse {
    private ByteIlaReverse() {
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
            ila.toArray(array, offset + (length - 1) * stride, -stride, length() - (start + length), length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
