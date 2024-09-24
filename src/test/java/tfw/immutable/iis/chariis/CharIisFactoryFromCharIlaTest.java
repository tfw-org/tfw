package tfw.immutable.iis.chariis;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.charila.CharIla;
import tfw.immutable.ila.charila.CharIlaFromArray;

class CharIisFactoryFromCharIlaTest {
    @Test
    void testArguments() {
        assertThrows(IllegalArgumentException.class, () -> CharIisFactoryFromCharIla.create(null));
    }

    @Test
    void testFactory() throws IOException {
        final char[] expectedArray = new char[11];
        final CharIla ila = CharIlaFromArray.create(expectedArray);
        final CharIisFactory iisf = CharIisFactoryFromCharIla.create(ila);
        final CharIis iis = iisf.create();
        final char[] actualArray = new char[expectedArray.length];

        assertEquals(actualArray.length, iis.read(actualArray, 0, actualArray.length));
        assertArrayEquals(expectedArray, actualArray);
    }
}
// AUTO GENERATED FROM TEMPLATE
