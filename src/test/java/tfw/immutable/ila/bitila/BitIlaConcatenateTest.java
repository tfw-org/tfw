package tfw.immutable.ila.bitila;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.longila.LongIlaFromArray;

class BitIlaConcatenateTest {
    @Test
    void testCreateArguments() {
        final BitIla bitIla = BitIlaFromLongIla.create(LongIlaFromArray.create(new long[1]), 0, 64);

        assertThrows(IllegalArgumentException.class, () -> BitIlaConcatenate.create(null, bitIla));
        assertThrows(IllegalArgumentException.class, () -> BitIlaConcatenate.create(bitIla, null));
    }

    @Test
    void testLength() throws IOException {
        final long length = 64;
        final BitIla bitIla1 = BitIlaFromLongIla.create(LongIlaFromArray.create(new long[1]), 0, length);
        final BitIla bitIla2 = BitIlaFromLongIla.create(LongIlaFromArray.create(new long[1]), 0, length);
        final BitIla bitIla = BitIlaConcatenate.create(bitIla1, bitIla2);

        assertEquals(2 * length, bitIla.lengthInBits());
    }

    @Test
    void testGetArguments() throws IOException {
        final BitIla bitIla1 = BitIlaFromLongIla.create(LongIlaFromArray.create(new long[1]), 0, 64);
        final BitIla bitIla2 = BitIlaFromLongIla.create(LongIlaFromArray.create(new long[1]), 0, 64);

        BitIlaCheck.checkGetArguments(BitIlaConcatenate.create(bitIla1, bitIla2));
    }
}
