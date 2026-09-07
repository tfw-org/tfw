package tfw.immutable.ila;

import java.io.IOException;

public final class IlaReverseUtil {
    @FunctionalInterface
    public interface ChunkFunction {
        void process(long sourcePosition, int destinationOffset, int amount) throws IOException;
    }

    private IlaReverseUtil() {
        // non-instantiable class
    }

    public static void reverse(
            long sourceLength, int offset, long start, int length, int bufferLength, ChunkFunction function)
            throws IOException {
        int destinationOffset = offset + length;
        long sourcePosition = sourceLength - start;
        int remaining = length;

        while (remaining > 0) {
            final int amount = Math.min(remaining, bufferLength);

            sourcePosition -= amount;
            destinationOffset -= amount;

            function.process(sourcePosition, destinationOffset, amount);

            remaining -= amount;
        }
    }
}
