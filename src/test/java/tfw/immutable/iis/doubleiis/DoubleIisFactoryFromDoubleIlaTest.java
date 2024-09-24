package tfw.immutable.iis.doubleiis;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.doubleila.DoubleIlaFromArray;

class DoubleIisFactoryFromDoubleIlaTest {
    @Test
    void testArguments() {
        assertThrows(IllegalArgumentException.class, () -> DoubleIisFactoryFromDoubleIla.create(null));
    }

    @Test
    void testFactory() throws IOException {
        final double[] expectedArray = new double[11];
        final DoubleIla ila = DoubleIlaFromArray.create(expectedArray);
        final DoubleIisFactory iisf = DoubleIisFactoryFromDoubleIla.create(ila);
        final DoubleIis iis = iisf.create();
        final double[] actualArray = new double[expectedArray.length];

        assertEquals(actualArray.length, iis.read(actualArray, 0, actualArray.length));
        assertArrayEquals(expectedArray, actualArray);
    }
}
// AUTO GENERATED FROM TEMPLATE
