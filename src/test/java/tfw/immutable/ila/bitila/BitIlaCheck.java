package tfw.immutable.ila.bitila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;

public final class BitIlaCheck {
    private BitIlaCheck() {}

    public static void checkGetArguments(final BitIla ila) throws IOException {
        final long ilaLength = ila.lengthInBits();
        final long[] array = new long[1];

        assertThatThrownBy(() -> ila.get(null, 0, 0, 1)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> ila.get(array, -1, 0, 1)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> ila.get(array, 0, -1, 1)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> ila.get(array, 0, 0, -1)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> ila.get(array, array.length * (long) Long.SIZE, 0, 1))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> ila.get(array, 0, ilaLength * Long.SIZE, 1))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
