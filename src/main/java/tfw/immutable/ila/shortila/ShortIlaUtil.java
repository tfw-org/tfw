package tfw.immutable.ila.shortila;

import java.io.IOException;

public final class ShortIlaUtil {
    private ShortIlaUtil() {}

    public static short[] toArray(final ShortIla shortIla) throws IOException {
        return toArray(shortIla, 0, (int) Math.min(shortIla.length(), Integer.MAX_VALUE));
    }

    public static short[] toArray(final ShortIla shortIla, final long ilaStart, int length) throws IOException {
        short[] result = new short[length];

        shortIla.toArray(result, 0, ilaStart, length);

        return result;
    }
}
// AUTO GENERATED FROM TEMPLATE
