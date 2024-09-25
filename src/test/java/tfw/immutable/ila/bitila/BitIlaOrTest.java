package tfw.immutable.ila.bitila;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.longila.LongIlaFromArray;

class BitIlaOrTest {
    @Test
    void testAndArguments() {
        assertThrows(IllegalArgumentException.class, () -> BitIlaOr.or(null, 0, new long[1], 0, 0));
        assertThrows(IllegalArgumentException.class, () -> BitIlaOr.or(new long[1], -1, new long[1], 0, 0));
        assertThrows(IllegalArgumentException.class, () -> BitIlaOr.or(new long[1], 0, new long[1], 0, 0));
        assertThrows(IllegalArgumentException.class, () -> BitIlaOr.or(new long[1], 0, new long[1], -1, 0));
        assertThrows(IllegalArgumentException.class, () -> BitIlaOr.or(new long[1], 0, new long[1], 0, -1));
        assertThrows(IllegalArgumentException.class, () -> BitIlaOr.or(new long[1], 60, new long[1], 0, 5));
        assertThrows(IllegalArgumentException.class, () -> BitIlaOr.or(new long[1], 0, new long[1], 60, 5));
    }

    @Test
    void testCreateArguments() {
        final BitIla bitIla = BitIlaFromLongIla.create(LongIlaFromArray.create(new long[1]), 0, 64);

        assertThrows(IllegalArgumentException.class, () -> BitIlaOr.create(null, bitIla));
        assertThrows(IllegalArgumentException.class, () -> BitIlaOr.create(bitIla, null));
    }

    @Test
    void testLength() throws IOException {
        final long length = 64;
        final BitIla bitIla1 = BitIlaFromLongIla.create(LongIlaFromArray.create(new long[1]), 0, length);
        final BitIla bitIla2 = BitIlaFromLongIla.create(LongIlaFromArray.create(new long[1]), 0, length);
        final BitIla bitIla = BitIlaOr.create(bitIla1, bitIla2);

        assertEquals(length, bitIla.lengthInBits());
    }

    @Test
    void testGetArguments() throws IOException {
        final BitIla bitIla1 = BitIlaFromLongIla.create(LongIlaFromArray.create(new long[1]), 0, 64);
        final BitIla bitIla2 = BitIlaFromLongIla.create(LongIlaFromArray.create(new long[1]), 0, 64);

        BitIlaCheck.checkGetArguments(BitIlaOr.create(bitIla1, bitIla2));
    }
}
