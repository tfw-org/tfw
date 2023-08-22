package tfw.immutable.ila.byteila;

import java.io.IOException;
import tfw.check.Argument;

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

        protected void toArrayImpl(byte[] array, int offset, long start, int length) throws IOException {
            ila.toArray(array, offset, start, length);

            for (int ii = offset; length > 0; ii++, --length) {
                array[ii] = (byte) -array[ii];
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
