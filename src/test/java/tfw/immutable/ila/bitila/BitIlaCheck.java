package tfw.immutable.ila.bitila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

public final class BitIlaCheck {
    private BitIlaCheck() {}

    public static void checkGetArguments(final BitIla ila) throws IOException {
        final long ilaLength = ila.lengthInBits();
        final long[] array = new long[1];

        assertThrows(IllegalArgumentException.class, () -> ila.get(null, 0, 0, 1));
        assertThrows(IllegalArgumentException.class, () -> ila.get(array, -1, 0, 1));
        assertThrows(IllegalArgumentException.class, () -> ila.get(array, 0, -1, 1));
        assertThrows(IllegalArgumentException.class, () -> ila.get(array, 0, 0, -1));
        assertThrows(IllegalArgumentException.class, () -> ila.get(array, array.length * (long) Long.SIZE, 0, 1));
        assertThrows(IllegalArgumentException.class, () -> ila.get(array, 0, ilaLength * Long.SIZE, 1));
    }
}
