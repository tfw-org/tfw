package tfw.immutable.ila.byteila;

import java.io.IOException;
import tfw.check.Argument;

public final class ByteIlaFromArray {
    private ByteIlaFromArray() {
        // non-instantiable class
    }

    public static ByteIla create(byte[] array) {
        Argument.assertNotNull(array, "array");

        return new ByteIlaImpl(array);
    }

    private static class ByteIlaImpl extends AbstractByteIla {
        private final byte[] array;

        private ByteIlaImpl(byte[] array) {
            this.array = array;
        }

        @Override
        protected long lengthImpl() {
            return array.length;
        }

        @Override
        protected void getImpl(byte[] array, int offset, long start, int length) {
            System.arraycopy(this.array, (int) start, array, offset, length);
        }

        @Override
        protected void closeImpl() throws IOException {
            // Nothing to do.
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
