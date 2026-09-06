package tfw.immutable.ila.booleanila;

import java.io.IOException;
import tfw.check.Argument;

public final class BooleanIlaReverse {
    private BooleanIlaReverse() {
        // non-instantiable class
    }

    public static BooleanIla create(BooleanIla ila, final boolean[] buffer) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotNull(buffer, "buffer");
        Argument.assertNotLessThan(buffer.length, 1, "buffer.length");

        return new BooleanIlaImpl(ila, buffer);
    }

    private static class BooleanIlaImpl extends AbstractBooleanIla {
        private final BooleanIla ila;
        private final boolean[] buffer;

        private BooleanIlaImpl(BooleanIla ila, final boolean[] buffer) {
            this.ila = ila;
            this.buffer = buffer;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return ila.length();
        }

        @Override
        protected void getImpl(boolean[] array, int offset, long start, int length) throws IOException {
            final boolean[] reverseBuffer = buffer.clone();

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
