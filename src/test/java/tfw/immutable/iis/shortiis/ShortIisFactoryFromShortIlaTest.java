package tfw.immutable.iis.shortiis;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.shortila.ShortIla;
import tfw.immutable.ila.shortila.ShortIlaFromArray;

class ShortIisFactoryFromShortIlaTest {
    @Test
    void testArguments() {
        assertThrows(IllegalArgumentException.class, () -> ShortIisFactoryFromShortIla.create(null));
    }

    @Test
    void testFactory() throws IOException {
        final short[] expectedArray = new short[11];
        final ShortIla ila = ShortIlaFromArray.create(expectedArray);
        final ShortIisFactory iisf = ShortIisFactoryFromShortIla.create(ila);
        final ShortIis iis = iisf.create();
        final short[] actualArray = new short[expectedArray.length];

        assertEquals(actualArray.length, iis.read(actualArray, 0, actualArray.length));
        assertArrayEquals(expectedArray, actualArray);
    }
}
// AUTO GENERATED FROM TEMPLATE
