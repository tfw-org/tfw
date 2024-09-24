package tfw.immutable.iis.longiis;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaFromArray;

class LongIisFactoryFromLongIlaTest {
    @Test
    void testArguments() {
        assertThrows(IllegalArgumentException.class, () -> LongIisFactoryFromLongIla.create(null));
    }

    @Test
    void testFactory() throws IOException {
        final long[] expectedArray = new long[11];
        final LongIla ila = LongIlaFromArray.create(expectedArray);
        final LongIisFactory iisf = LongIisFactoryFromLongIla.create(ila);
        final LongIis iis = iisf.create();
        final long[] actualArray = new long[expectedArray.length];

        assertEquals(actualArray.length, iis.read(actualArray, 0, actualArray.length));
        assertArrayEquals(expectedArray, actualArray);
    }
}
// AUTO GENERATED FROM TEMPLATE
