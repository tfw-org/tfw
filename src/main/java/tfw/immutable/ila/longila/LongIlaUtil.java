package tfw.immutable.ila.longila;

import tfw.immutable.DataInvalidException;

public final class LongIlaUtil {
    private LongIlaUtil() {}

    public static long[] toArray(final LongIla longIla) throws DataInvalidException {
        return toArray(longIla, 0, (int) Math.min(longIla.length(), Integer.MAX_VALUE));
    }

    public static long[] toArray(final LongIla longIla, final long ilaStart, final int length)
            throws DataInvalidException {
        long[] result = new long[length];

        longIla.toArray(result, 0, ilaStart, length);

        return result;
    }
}
// AUTO GENERATED FROM TEMPLATE
