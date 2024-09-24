package tfw.immutable.iis.floatiis;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.floatila.FloatIla;
import tfw.immutable.ila.floatila.FloatIlaFromArray;

class FloatIisFactoryFromFloatIlaTest {
    @Test
    void testArguments() {
        assertThrows(IllegalArgumentException.class, () -> FloatIisFactoryFromFloatIla.create(null));
    }

    @Test
    void testFactory() throws IOException {
        final float[] expectedArray = new float[11];
        final FloatIla ila = FloatIlaFromArray.create(expectedArray);
        final FloatIisFactory iisf = FloatIisFactoryFromFloatIla.create(ila);
        final FloatIis iis = iisf.create();
        final float[] actualArray = new float[expectedArray.length];

        assertEquals(actualArray.length, iis.read(actualArray, 0, actualArray.length));
        assertArrayEquals(expectedArray, actualArray);
    }
}
// AUTO GENERATED FROM TEMPLATE
