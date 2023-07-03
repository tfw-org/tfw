package tfw.immutable.ila.booleanila;

import tfw.immutable.DataInvalidException;

public final class BooleanIlaUtil {
    private BooleanIlaUtil() {}

    public static final boolean[] toArray(final BooleanIla booleanIla) throws DataInvalidException {
        return toArray(booleanIla, 0, (int) Math.min(booleanIla.length(), Integer.MAX_VALUE));
    }

    public static final boolean[] toArray(final BooleanIla booleanIla, final long start, final int length)
            throws DataInvalidException {
        boolean[] result = new boolean[length];

        booleanIla.toArray(result, 0, start, length);

        return result;
    }
}
// AUTO GENERATED FROM TEMPLATE
