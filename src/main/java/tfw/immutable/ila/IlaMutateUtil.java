package tfw.immutable.ila;

import java.io.IOException;

public final class IlaMutateUtil {
    @FunctionalInterface
    public interface GetFunction {
        void get(int destinationOffset, long sourceStart, int length) throws IOException;
    }

    @FunctionalInterface
    public interface SetFunction {
        void set(int destinationOffset);
    }

    private IlaMutateUtil() {
        // non-instantiable class
    }

    public static void get(
            long index, long start, int length, int offset, GetFunction getFunction, SetFunction setFunction)
            throws IOException {

        final long startPlusLength = start + length;

        if (index < start || index >= startPlusLength) {
            getFunction.get(offset, start, length);
        } else {
            final int indexMinusStart = (int) (index - start);

            if (index > start) {
                getFunction.get(offset, start, indexMinusStart);
            }

            setFunction.set(offset + indexMinusStart);

            if (index <= startPlusLength) {
                getFunction.get(offset + indexMinusStart + 1, index + 1, length - indexMinusStart - 1);
            }
        }
    }
}
