package tfw.immutable.ila.charila;

import tfw.immutable.DataInvalidException;

public final class CharIlaUtil {
    private CharIlaUtil() {}

    public static char[] toArray(final CharIla charIla) throws DataInvalidException {
        return toArray(charIla, 0, (int) Math.min(charIla.length(), Integer.MAX_VALUE));
    }

    public static char[] toArray(final CharIla charIla, final long ilaStart, final int length)
            throws DataInvalidException {
        char[] result = new char[length];

        charIla.toArray(result, 0, ilaStart, length);

        return result;
    }
}
// AUTO GENERATED FROM TEMPLATE
