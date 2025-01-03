package tfw.immutable.ila.bitila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaFromArray;

final class BitIlaFindTest {
    @Test
    void bitIlaFindInvalidParametersTest() {
        final LongIla longIla = LongIlaFromArray.create(new long[1]);
        final BitIla bitIla = BitIlaFromLongIla.create(longIla, 0, 63);

        assertThatThrownBy(() -> BitIlaFind.find((BitIla) null, 0, 0, new boolean[1], new long[1]))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> BitIlaFind.find(bitIla, -1, 0, new boolean[1], new long[1]))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> BitIlaFind.find(bitIla, 0, -1, new boolean[1], new long[1]))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> BitIlaFind.find(bitIla, 0, 0, null, new long[1]))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> BitIlaFind.find(bitIla, 0, 0, new boolean[0], new long[1]))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> BitIlaFind.find(bitIla, 0, 0, new boolean[1], null))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> BitIlaFind.find(bitIla, 0, 0, new boolean[1], new long[0]))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void bitArrayFindInvalidParametersTest() {
        assertThatThrownBy(() -> BitIlaFind.find((long[]) null, 0, 0, new boolean[1], new long[1]))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> BitIlaFind.find(new long[0], -1, 0, new boolean[1], new long[1]))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> BitIlaFind.find(new long[0], 0, -1, new boolean[1], new long[1]))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> BitIlaFind.find(new long[0], 0, 0, null, new long[1]))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> BitIlaFind.find(new long[0], 0, 0, new boolean[0], new long[1]))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> BitIlaFind.find(new long[0], 0, 0, new boolean[1], null))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> BitIlaFind.find(new long[0], 0, 0, new boolean[1], new long[0]))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
