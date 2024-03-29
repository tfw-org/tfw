package tfw.immutable.ila.byteila;

import java.io.IOException;
import tfw.check.Argument;

public final class ByteIlaNegate {
    private ByteIlaNegate() {
        // non-instantiable class
    }

    public static ByteIla create(ByteIla ila) {
        Argument.assertNotNull(ila, "ila");

        return new ByteIlaImpl(ila);
    }

    private static class ByteIlaImpl extends AbstractByteIla {
        private final ByteIla ila;

        private ByteIlaImpl(ByteIla ila) {
            this.ila = ila;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return ila.length();
        }

        @Override
        protected void getImpl(byte[] array, int offset, long start, int length) throws IOException {
            ila.get(array, offset, start, length);

            for (int ii = offset; length > 0; ii++, --length) {
                array[ii] = (byte) -array[ii];
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
