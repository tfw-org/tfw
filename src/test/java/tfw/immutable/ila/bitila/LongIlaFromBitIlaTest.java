package tfw.immutable.ila.bitila;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaCheck;
import tfw.immutable.ila.longila.LongIlaFromArray;

class LongIlaFromBitIlaTest {
    @Test
    void testCreateArguments() {
        assertThrows(IllegalArgumentException.class, () -> LongIlaFromBitIla.create(null));
    }

    @Test
    void testLength() throws IOException {
        final long length = 64;
        final BitIla bitIla1 = BitIlaFromLongIla.create(LongIlaFromArray.create(new long[1]), 0, length);
        final LongIla longIla = LongIlaFromBitIla.create(bitIla1);

        assertEquals(length / Long.SIZE, longIla.length());
    }

    @Test
    void testGetArguments() throws IOException {
        final BitIla bitIla1 = BitIlaFromLongIla.create(LongIlaFromArray.create(new long[1]), 0, 64);

        LongIlaCheck.checkGetArguments(LongIlaFromBitIla.create(bitIla1));
    }
}
