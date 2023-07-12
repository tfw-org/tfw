package tfw.immutable.ila.intila;

import tfw.immutable.DataInvalidException;

public final class IntIlaUtil {
    private IntIlaUtil() {}

    public static int[] toArray(final IntIla intIla) throws DataInvalidException {
        return toArray(intIla, 0, (int) Math.min(intIla.length(), Integer.MAX_VALUE));
    }

    public static int[] toArray(final IntIla intIla, final long ilaStart, final int length)
            throws DataInvalidException {
        int[] result = new int[length];

        intIla.toArray(result, 0, ilaStart, length);

        return result;
    }
}
// AUTO GENERATED FROM TEMPLATE
