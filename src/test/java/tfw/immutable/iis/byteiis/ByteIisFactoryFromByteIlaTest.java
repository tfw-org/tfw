package tfw.immutable.iis.byteiis;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaFromArray;

class ByteIisFactoryFromByteIlaTest {
    @Test
    void testArguments() {
        assertThrows(IllegalArgumentException.class, () -> ByteIisFactoryFromByteIla.create(null));
    }

    @Test
    void testFactory() throws IOException {
        final byte[] expectedArray = new byte[11];
        final ByteIla ila = ByteIlaFromArray.create(expectedArray);
        final ByteIisFactory iisf = ByteIisFactoryFromByteIla.create(ila);
        final ByteIis iis = iisf.create();
        final byte[] actualArray = new byte[expectedArray.length];

        assertEquals(actualArray.length, iis.read(actualArray, 0, actualArray.length));
        assertArrayEquals(expectedArray, actualArray);
    }
}
// AUTO GENERATED FROM TEMPLATE
