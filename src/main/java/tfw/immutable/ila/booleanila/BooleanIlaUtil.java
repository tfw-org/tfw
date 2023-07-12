package tfw.immutable.ila.booleanila;

import tfw.immutable.DataInvalidException;

public final class BooleanIlaUtil {
    private BooleanIlaUtil() {}

    public static boolean[] toArray(final BooleanIla booleanIla) throws DataInvalidException {
        return toArray(booleanIla, 0, (int) Math.min(booleanIla.length(), Integer.MAX_VALUE));
    }

    public static boolean[] toArray(final BooleanIla booleanIla, final long ilaStart, final int length)
            throws DataInvalidException {
        boolean[] result = new boolean[length];

        booleanIla.toArray(result, 0, ilaStart, length);

        return result;
    }
}
// AUTO GENERATED FROM TEMPLATE
