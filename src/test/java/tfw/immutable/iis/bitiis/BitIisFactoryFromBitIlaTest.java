package tfw.immutable.iis.bitiis;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.bitila.BitIla;
import tfw.immutable.ila.bitila.BitIlaFromLongIla;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaFromArray;

class BitIisFactoryFromBitIlaTest {
    @Test
    void testArguments() {
        assertThrows(IllegalArgumentException.class, () -> BitIisFactoryFromBitIla.create(null));
    }

    @Test
    void testFactory() throws IOException {
        final long[] expectedArray = new long[1];
        final LongIla longIla = LongIlaFromArray.create(expectedArray);
        final BitIla ila = BitIlaFromLongIla.create(longIla, 0, 12);
        final BitIisFactory iisf = BitIisFactoryFromBitIla.create(ila);
        final BitIis iis = iisf.create();
        final long[] actualArray = new long[expectedArray.length];

        assertEquals(actualArray.length, iis.read(actualArray, 0, actualArray.length));
        assertArrayEquals(expectedArray, actualArray);
    }
}
