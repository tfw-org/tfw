package tfw.immutable.ila.bitila;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import org.junit.jupiter.api.Test;

class BitIlaFillTest {
    @Test
    void testAndArguments() {
        assertThrows(IllegalArgumentException.class, () -> BitIlaFill.fill(null, 0, 0, true));
        assertThrows(IllegalArgumentException.class, () -> BitIlaFill.fill(new long[1], -1, 0, true));
        assertThrows(IllegalArgumentException.class, () -> BitIlaFill.fill(new long[1], 0, -1, true));
    }

    @Test
    void testCreateArguments() {
        assertThrows(IllegalArgumentException.class, () -> BitIlaFill.create(true, -1));
    }

    @Test
    void testLength() throws IOException {
        final long length = 64;
        final BitIla bitIla = BitIlaFill.create(true, length);

        assertEquals(length, bitIla.lengthInBits());
    }

    @Test
    void testGetArguments() throws IOException {
        BitIlaCheck.checkGetArguments(BitIlaFill.create(true, 64));
    }
}
