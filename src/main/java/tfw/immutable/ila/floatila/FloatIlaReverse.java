package tfw.immutable.ila.floatila;

import java.io.IOException;
import tfw.check.Argument;

public final class FloatIlaReverse {
    private FloatIlaReverse() {
        // non-instantiable class
    }

    public static FloatIla create(FloatIla ila, final float[] buffer) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotNull(buffer, "buffer");
        Argument.assertNotLessThan(buffer.length, 1, "buffer.length");

        return new FloatIlaImpl(ila, buffer);
    }

    private static class FloatIlaImpl extends AbstractFloatIla {
        private final FloatIla ila;
        private final float[] buffer;

        private FloatIlaImpl(FloatIla ila, final float[] buffer) {
            this.ila = ila;
            this.buffer = buffer;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return ila.length();
        }

        @Override
        protected void getImpl(float[] array, int offset, long start, int length) throws IOException {
            final float[] reverseBuffer = buffer.clone();

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
