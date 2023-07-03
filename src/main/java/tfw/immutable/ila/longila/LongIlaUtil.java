package tfw.immutable.ila.longila;

import tfw.immutable.DataInvalidException;

public final class LongIlaUtil {
    private LongIlaUtil() {}

    public static final long[] toArray(final LongIla longIla) throws DataInvalidException {
        return toArray(longIla, 0, (int) Math.min(longIla.length(), Integer.MAX_VALUE));
    }

    public static final long[] toArray(final LongIla longIla, final long start, final int length)
            throws DataInvalidException {
        long[] result = new long[length];

        longIla.toArray(result, 0, start, length);

        return result;
    }
}
// AUTO GENERATED FROM TEMPLATE
