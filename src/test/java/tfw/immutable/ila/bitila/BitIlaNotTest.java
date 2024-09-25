package tfw.immutable.ila.bitila;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.longila.LongIlaFromArray;

class BitIlaNotTest {
    @Test
    void testAndArguments() {
        assertThrows(IllegalArgumentException.class, () -> BitIlaNot.not(null, 0, 0));
        assertThrows(IllegalArgumentException.class, () -> BitIlaNot.not(new long[1], -1, 0));
        assertThrows(IllegalArgumentException.class, () -> BitIlaNot.not(new long[1], 0, -1));
    }

    @Test
    void testCreateArguments() {
        assertThrows(IllegalArgumentException.class, () -> BitIlaNot.create(null));
    }

    @Test
    void testLength() throws IOException {
        final long length = 64;
        final BitIla bitIla = BitIlaFromLongIla.create(LongIlaFromArray.create(new long[1]), 0, length);
        final BitIla bitIlaNot = BitIlaNot.create(bitIla);

        assertEquals(length, bitIlaNot.lengthInBits());
    }

    @Test
    void testGetArguments() throws IOException {
        final BitIla bitIla1 = BitIlaFromLongIla.create(LongIlaFromArray.create(new long[1]), 0, 64);

        BitIlaCheck.checkGetArguments(BitIlaNot.create(bitIla1));
    }
}
