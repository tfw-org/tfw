package tfw.immutable.ila;

import java.io.IOException;

public final class IlaConcatenateUtil {
    @FunctionalInterface
    public interface GetFunction {
        void get(int offset, long start, int length) throws IOException;
    }

    private IlaConcatenateUtil() {
        // non-instantiable class
    }

    public static void get(
            long leftLength, int offset, long start, int length, GetFunction leftGet, GetFunction rightGet)
            throws IOException {

        final long leftLastIndex = leftLength - 1;

        if (start + length <= leftLastIndex) {
            leftGet.get(offset, start, length);
        } else if (start > leftLastIndex) {
            rightGet.get(offset, start - leftLength, length);
        } else {
            final int leftAmount = (int) (leftLength - start);

            leftGet.get(offset, start, leftAmount);
            rightGet.get(offset + leftAmount, 0, length - leftAmount);
        }
    }
}
