package tfw.immutable.ila.bitila;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.longila.LongIlaFromArray;

class BitIlaAndTest {
    @Test
    void testAndArguments() {
        assertThrows(IllegalArgumentException.class, () -> BitIlaAnd.and(null, 0, new long[1], 0, 0));
        assertThrows(IllegalArgumentException.class, () -> BitIlaAnd.and(new long[1], -1, new long[1], 0, 0));
        assertThrows(IllegalArgumentException.class, () -> BitIlaAnd.and(new long[1], 0, new long[1], 0, 0));
        assertThrows(IllegalArgumentException.class, () -> BitIlaAnd.and(new long[1], 0, new long[1], -1, 0));
        assertThrows(IllegalArgumentException.class, () -> BitIlaAnd.and(new long[1], 0, new long[1], 0, -1));
        assertThrows(IllegalArgumentException.class, () -> BitIlaAnd.and(new long[1], 60, new long[1], 0, 5));
        assertThrows(IllegalArgumentException.class, () -> BitIlaAnd.and(new long[1], 0, new long[1], 60, 5));
    }

    @Test
    void testCreateArguments() {
        final BitIla bitIla = BitIlaFromLongIla.create(LongIlaFromArray.create(new long[1]), 0, 64);

        assertThrows(IllegalArgumentException.class, () -> BitIlaAnd.create(null, bitIla));
        assertThrows(IllegalArgumentException.class, () -> BitIlaAnd.create(bitIla, null));
    }

    @Test
    void testLength() throws IOException {
        final long length = 64;
        final BitIla bitIla1 = BitIlaFromLongIla.create(LongIlaFromArray.create(new long[1]), 0, length);
        final BitIla bitIla2 = BitIlaFromLongIla.create(LongIlaFromArray.create(new long[1]), 0, length);
        final BitIla bitIla = BitIlaAnd.create(bitIla1, bitIla2);

        assertEquals(length, bitIla.lengthInBits());
    }

    @Test
    void testGetArguments() throws IOException {
        final BitIla bitIla1 = BitIlaFromLongIla.create(LongIlaFromArray.create(new long[1]), 0, 64);
        final BitIla bitIla2 = BitIlaFromLongIla.create(LongIlaFromArray.create(new long[1]), 0, 64);

        BitIlaCheck.checkGetArguments(BitIlaAnd.create(bitIla1, bitIla2));
    }

    @Test
    void testOne() {
        final int numberOfLongs = 3;
        final long[] zeroBitLongs = new long[numberOfLongs];
        final long[] oneBitLongs = new long[numberOfLongs];
        final long[] expectedLongs = new long[numberOfLongs];

        for (int length = 0; length < Long.SIZE; length++) {
            for (int j = 0; j < expectedLongs.length * Long.SIZE; j++) {
                BitIlaUtil.setBit(zeroBitLongs, j, j < length ? 0 : 1);
                BitIlaUtil.setBit(oneBitLongs, j, j < length ? 1 : 0);
            }
        }
    }
}
