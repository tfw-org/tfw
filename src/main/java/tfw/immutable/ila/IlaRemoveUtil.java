package tfw.immutable.ila;

import java.io.IOException;

public final class IlaRemoveUtil {
    @FunctionalInterface
    public interface GetFunction {
        void get(int destinationOffset, long sourceStart, int length) throws IOException;
    }

    private IlaRemoveUtil() {
        // non-instantiable class
    }

    public static void get(long index, long start, int length, int offset, GetFunction getFunction) throws IOException {

        final long startPlusLength = start + length;

        if ((index - 1) < start) {
            getFunction.get(offset, start + 1, length);
        } else if ((index + 1) > startPlusLength) {
            getFunction.get(offset, start, length);
        } else {
            final int indexMinusStart = (int) (index - start);

            getFunction.get(offset, start, indexMinusStart);
            getFunction.get(offset + indexMinusStart, index + 1, length - indexMinusStart);
        }
    }
}
