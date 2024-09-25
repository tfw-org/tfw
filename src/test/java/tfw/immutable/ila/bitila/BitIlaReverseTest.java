package tfw.immutable.ila.bitila;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.longila.LongIlaFromArray;

class BitIlaReverseTest {
    @Test
    void testAndArguments() {
        assertThrows(IllegalArgumentException.class, () -> BitIlaReverse.reverse(null, 0, 0));
        assertThrows(IllegalArgumentException.class, () -> BitIlaReverse.reverse(new long[1], -1, 0));
        assertThrows(IllegalArgumentException.class, () -> BitIlaReverse.reverse(new long[1], 0, -1));
    }

    @Test
    void testCreateArguments() {
        assertThrows(IllegalArgumentException.class, () -> BitIlaReverse.create(null));
    }

    @Test
    void testLength() throws IOException {
        final long length = 64;
        final BitIla bitIla1 = BitIlaFromLongIla.create(LongIlaFromArray.create(new long[1]), 0, length);
        final BitIla bitIla = BitIlaReverse.create(bitIla1);

        assertEquals(length, bitIla.lengthInBits());
    }

    @Test
    void testGetArguments() throws IOException {
        final BitIla bitIla1 = BitIlaFromLongIla.create(LongIlaFromArray.create(new long[1]), 0, 64);

        BitIlaCheck.checkGetArguments(BitIlaReverse.create(bitIla1));
    }
}
