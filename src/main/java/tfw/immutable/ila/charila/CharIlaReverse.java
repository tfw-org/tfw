package tfw.immutable.ila.charila;

import java.io.IOException;
import tfw.check.Argument;

public final class CharIlaReverse {
    private CharIlaReverse() {
        // non-instantiable class
    }

    public static CharIla create(CharIla ila, final char[] buffer) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotNull(buffer, "buffer");
        Argument.assertNotLessThan(buffer.length, 1, "buffer.length");

        return new CharIlaImpl(ila, buffer);
    }

    private static class CharIlaImpl extends AbstractCharIla {
        private final CharIla ila;
        private final char[] buffer;

        private CharIlaImpl(CharIla ila, final char[] buffer) {
            this.ila = ila;
            this.buffer = buffer;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return ila.length();
        }

        @Override
        protected void getImpl(char[] array, int offset, long start, int length) throws IOException {
            final char[] reverseBuffer = buffer.clone();

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
