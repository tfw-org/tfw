package tfw.immutable.ila.byteila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public final class ByteIlaReverse {
    private ByteIlaReverse() {
        // non-instantiable class
    }

    public static ByteIla create(ByteIla ila, final byte[] buffer) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotNull(buffer, "buffer");

        return new MyByteIla(ila, buffer);
    }

    private static class MyByteIla extends AbstractByteIla {
        private final ByteIla ila;
        private final byte[] buffer;

        MyByteIla(ByteIla ila, final byte[] buffer) {
            super(ila.length());

            this.ila = ila;
            this.buffer = buffer;
        }

        protected void toArrayImpl(byte[] array, int offset, long start, int length) throws DataInvalidException {
            final StridedByteIla stridedByteIla = new StridedByteIla(ila, buffer.clone());

            stridedByteIla.toArray(array, offset + (length - 1), -1, length() - (start + length), length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
