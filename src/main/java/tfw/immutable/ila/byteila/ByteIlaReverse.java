package tfw.immutable.ila.byteila;

import java.io.IOException;
import tfw.check.Argument;

public final class ByteIlaReverse {
    private ByteIlaReverse() {
        // non-instantiable class
    }

    public static ByteIla create(ByteIla ila, final byte[] buffer) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotNull(buffer, "buffer");
        Argument.assertNotLessThan(buffer.length, 1, "buffer.length");

        return new ByteIlaImpl(ila, buffer);
    }

    private static class ByteIlaImpl extends AbstractByteIla {
        private final ByteIla ila;
        private final byte[] buffer;

        private ByteIlaImpl(ByteIla ila, final byte[] buffer) {
            this.ila = ila;
            this.buffer = buffer;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return ila.length();
        }

        @Override
        protected void getImpl(byte[] array, int offset, long start, int length) throws IOException {
            final byte[] reverseBuffer = buffer.clone();

            int destinationOffset = offset + length;
            long sourcePosition = length() - start;
            int remaining = length;

            while (remaining > 0) {
                final int amount = Math.min(remaining, reverseBuffer.length);

                sourcePosition -= amount;
                destinationOffset -= amount;

                ila.get(reverseBuffer, 0, sourcePosition, amount);

                for (int i = 0; i < amount; i++) {
                    array[destinationOffset + i] = reverseBuffer[amount - 1 - i];
                }

                remaining -= amount;
            }
        }

        @Override
        protected void closeImpl() throws IOException {
            ila.close();
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
