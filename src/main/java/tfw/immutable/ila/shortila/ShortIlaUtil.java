package tfw.immutable.ila.shortila;

import tfw.immutable.DataInvalidException;

public final class ShortIlaUtil {
    private ShortIlaUtil() {}

    public static final short[] toArray(final ShortIla shortIla) throws DataInvalidException {
        return toArray(shortIla, 0, (int) Math.min(shortIla.length(), Integer.MAX_VALUE));
    }

    public static final short[] toArray(final ShortIla shortIla, final long start, final int length)
            throws DataInvalidException {
        short[] result = new short[length];

        shortIla.toArray(result, 0, start, length);

        return result;
    }
}
// AUTO GENERATED FROM TEMPLATE
