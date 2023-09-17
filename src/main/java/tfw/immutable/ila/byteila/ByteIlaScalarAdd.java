package tfw.immutable.ila.byteila;

import java.io.IOException;
import tfw.check.Argument;

public final class ByteIlaScalarAdd {
    private ByteIlaScalarAdd() {
        // non-instantiable class
    }

    public static ByteIla create(ByteIla ila, byte scalar) {
        Argument.assertNotNull(ila, "ila");

        return new ByteIlaImpl(ila, scalar);
    }

    private static class ByteIlaImpl extends AbstractByteIla {
        private final ByteIla ila;
        private final byte scalar;

        private ByteIlaImpl(ByteIla ila, byte scalar) {
            this.ila = ila;
            this.scalar = scalar;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return ila.length();
        }

        @Override
        protected void getImpl(byte[] array, int offset, long start, int length) throws IOException {
            ila.get(array, offset, start, length);

            for (int ii = offset; length > 0; ii++, --length) {
                array[ii] += scalar;
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
