package tfw.immutable.ila.bitila;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.longila.LongIlaFromArray;

class BitIlaXorTest {
    @Test
    void testAndArguments() {
        assertThrows(IllegalArgumentException.class, () -> BitIlaXor.xor(null, 0, new long[1], 0, 0));
        assertThrows(IllegalArgumentException.class, () -> BitIlaXor.xor(new long[1], -1, new long[1], 0, 0));
        assertThrows(IllegalArgumentException.class, () -> BitIlaXor.xor(new long[1], 0, new long[1], 0, 0));
        assertThrows(IllegalArgumentException.class, () -> BitIlaXor.xor(new long[1], 0, new long[1], -1, 0));
        assertThrows(IllegalArgumentException.class, () -> BitIlaXor.xor(new long[1], 0, new long[1], 0, -1));
        assertThrows(IllegalArgumentException.class, () -> BitIlaXor.xor(new long[1], 60, new long[1], 0, 5));
        assertThrows(IllegalArgumentException.class, () -> BitIlaXor.xor(new long[1], 0, new long[1], 60, 5));
    }

    @Test
    void testCreateArguments() {
        final BitIla bitIla = BitIlaFromLongIla.create(LongIlaFromArray.create(new long[1]), 0, 64);

        assertThrows(IllegalArgumentException.class, () -> BitIlaXor.create(null, bitIla));
        assertThrows(IllegalArgumentException.class, () -> BitIlaXor.create(bitIla, null));
    }

    @Test
    void testLength() throws IOException {
        final long length = 64;
        final BitIla bitIla1 = BitIlaFromLongIla.create(LongIlaFromArray.create(new long[1]), 0, length);
        final BitIla bitIla2 = BitIlaFromLongIla.create(LongIlaFromArray.create(new long[1]), 0, length);
        final BitIla bitIla = BitIlaXor.create(bitIla1, bitIla2);

        assertEquals(length, bitIla.length());
    }

    @Test
    void testGetArguments() throws IOException {
        final BitIla bitIla1 = BitIlaFromLongIla.create(LongIlaFromArray.create(new long[1]), 0, 64);
        final BitIla bitIla2 = BitIlaFromLongIla.create(LongIlaFromArray.create(new long[1]), 0, 64);

        BitIlaCheck.checkGetArguments(BitIlaXor.create(bitIla1, bitIla2));
    }
}
