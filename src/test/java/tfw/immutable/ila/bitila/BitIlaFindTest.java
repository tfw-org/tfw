package tfw.immutable.ila.bitila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaFromArray;

class BitIlaFindTest {
    @Test
    void testBitIlaFindInvalidParameters() {
        final LongIla longIla = LongIlaFromArray.create(new long[1]);
        final BitIla bitIla = BitIlaFromLongIla.create(longIla, 0, 63);

        assertThrows(
                IllegalArgumentException.class,
                () -> BitIlaFind.find((BitIla) null, 0, 0, new boolean[1], new long[1]));
        assertThrows(IllegalArgumentException.class, () -> BitIlaFind.find(bitIla, -1, 0, new boolean[1], new long[1]));
        assertThrows(IllegalArgumentException.class, () -> BitIlaFind.find(bitIla, 0, -1, new boolean[1], new long[1]));
        assertThrows(IllegalArgumentException.class, () -> BitIlaFind.find(bitIla, 0, 0, null, new long[1]));
        assertThrows(IllegalArgumentException.class, () -> BitIlaFind.find(bitIla, 0, 0, new boolean[0], new long[1]));
        assertThrows(IllegalArgumentException.class, () -> BitIlaFind.find(bitIla, 0, 0, new boolean[1], null));
        assertThrows(IllegalArgumentException.class, () -> BitIlaFind.find(bitIla, 0, 0, new boolean[1], new long[0]));
    }

    @Test
    void testBitArrayFindInvalidParameters() {
        assertThrows(
                IllegalArgumentException.class,
                () -> BitIlaFind.find((long[]) null, 0, 0, new boolean[1], new long[1]));
        assertThrows(
                IllegalArgumentException.class, () -> BitIlaFind.find(new long[0], -1, 0, new boolean[1], new long[1]));
        assertThrows(
                IllegalArgumentException.class, () -> BitIlaFind.find(new long[0], 0, -1, new boolean[1], new long[1]));
        assertThrows(IllegalArgumentException.class, () -> BitIlaFind.find(new long[0], 0, 0, null, new long[1]));
        assertThrows(
                IllegalArgumentException.class, () -> BitIlaFind.find(new long[0], 0, 0, new boolean[0], new long[1]));
        assertThrows(IllegalArgumentException.class, () -> BitIlaFind.find(new long[0], 0, 0, new boolean[1], null));
        assertThrows(
                IllegalArgumentException.class, () -> BitIlaFind.find(new long[0], 0, 0, new boolean[1], new long[0]));
    }
}
