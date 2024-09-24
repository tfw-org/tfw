package tfw.immutable.iis.intiis;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.intila.IntIla;
import tfw.immutable.ila.intila.IntIlaFromArray;

class IntIisFactoryFromIntIlaTest {
    @Test
    void testArguments() {
        assertThrows(IllegalArgumentException.class, () -> IntIisFactoryFromIntIla.create(null));
    }

    @Test
    void testFactory() throws IOException {
        final int[] expectedArray = new int[11];
        final IntIla ila = IntIlaFromArray.create(expectedArray);
        final IntIisFactory iisf = IntIisFactoryFromIntIla.create(ila);
        final IntIis iis = iisf.create();
        final int[] actualArray = new int[expectedArray.length];

        assertEquals(actualArray.length, iis.read(actualArray, 0, actualArray.length));
        assertArrayEquals(expectedArray, actualArray);
    }
}
// AUTO GENERATED FROM TEMPLATE
