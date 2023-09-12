package tfw.immutable.ila.booleanila;

import java.io.IOException;

public final class BooleanIlaUtil {
    private BooleanIlaUtil() {}

    public static boolean[] toArray(final BooleanIla booleanIla) throws IOException {
        return toArray(booleanIla, 0, (int) Math.min(booleanIla.length(), Integer.MAX_VALUE));
    }

    public static boolean[] toArray(final BooleanIla booleanIla, final long ilaStart, int length) throws IOException {
        boolean[] result = new boolean[length];

        booleanIla.get(result, 0, ilaStart, length);

        return result;
    }
}
// AUTO GENERATED FROM TEMPLATE
