package tfw.immutable.ila.objectila;

import java.io.IOException;
import tfw.check.Argument;

public final class ObjectIlaReverse {
    private ObjectIlaReverse() {
        // non-instantiable class
    }

    public static <T> ObjectIla<T> create(ObjectIla<T> ila, final T[] buffer) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotNull(buffer, "buffer");
        Argument.assertNotLessThan(buffer.length, 1, "buffer.length");

        return new ObjectIlaImpl<>(ila, buffer);
    }

    private static class ObjectIlaImpl<T> extends AbstractObjectIla<T> {
        private final ObjectIla<T> ila;
        private final T[] buffer;

        private ObjectIlaImpl(ObjectIla<T> ila, final T[] buffer) {
            this.ila = ila;
            this.buffer = buffer;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return ila.length();
        }

        @Override
        protected void getImpl(T[] array, int offset, long start, int length) throws IOException {
            final T[] reverseBuffer = buffer.clone();

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
