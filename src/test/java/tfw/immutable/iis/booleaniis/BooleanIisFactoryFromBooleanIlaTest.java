package tfw.immutable.iis.booleaniis;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.booleanila.BooleanIla;
import tfw.immutable.ila.booleanila.BooleanIlaFromArray;

class BooleanIisFactoryFromBooleanIlaTest {
    @Test
    void testArguments() {
        assertThrows(IllegalArgumentException.class, () -> BooleanIisFactoryFromBooleanIla.create(null));
    }

    @Test
    void testFactory() throws IOException {
        final boolean[] expectedArray = new boolean[11];
        final BooleanIla ila = BooleanIlaFromArray.create(expectedArray);
        final BooleanIisFactory iisf = BooleanIisFactoryFromBooleanIla.create(ila);
        final BooleanIis iis = iisf.create();
        final boolean[] actualArray = new boolean[expectedArray.length];

        assertEquals(actualArray.length, iis.read(actualArray, 0, actualArray.length));
        assertArrayEquals(expectedArray, actualArray);
    }
}
// AUTO GENERATED FROM TEMPLATE
