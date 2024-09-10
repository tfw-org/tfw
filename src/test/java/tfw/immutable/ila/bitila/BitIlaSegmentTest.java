package tfw.immutable.ila.bitila;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.longila.LongIlaFromArray;

class BitIlaSegmentTest {
    @Test
    void testCreateArguments() {
        final BitIla bitIla = BitIlaFromLongIla.create(LongIlaFromArray.create(new long[1]), 0, 64);

        assertThrows(IllegalArgumentException.class, () -> BitIlaSegment.create(null, 0, 0));
        assertThrows(IllegalArgumentException.class, () -> BitIlaSegment.create(bitIla, -1, 0));
        assertThrows(IllegalArgumentException.class, () -> BitIlaSegment.create(bitIla, 0, -1));
    }

    @Test
    void testLength() throws IOException {
        final long length = 128;
        final BitIla bitIla1 = BitIlaFromLongIla.create(LongIlaFromArray.create(new long[2]), 0, length);
        final BitIla bitIla = BitIlaSegment.create(bitIla1, 32, 64);

        assertEquals(length / 2, bitIla.length());
    }

    @Test
    void testGetArguments() throws IOException {
        final BitIla bitIla1 = BitIlaFromLongIla.create(LongIlaFromArray.create(new long[2]), 0, 128);

        BitIlaCheck.checkGetArguments(BitIlaSegment.create(bitIla1, 32, 64));
    }
}
