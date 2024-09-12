package tfw.immutable.ila.intila;

import java.io.IOException;

public final class IntIlaUtil {
    private IntIlaUtil() {}

    public static int[] toArray(final IntIla intIla) throws IOException {
        return toArray(intIla, 0, (int) Math.min(intIla.length(), Integer.MAX_VALUE));
    }

    public static int[] toArray(final IntIla intIla, final long ilaStart, int length) throws IOException {
        int[] result = new int[length];

        intIla.get(result, 0, ilaStart, length);

        return result;
    }
}
// AUTO GENERATED FROM TEMPLATE
