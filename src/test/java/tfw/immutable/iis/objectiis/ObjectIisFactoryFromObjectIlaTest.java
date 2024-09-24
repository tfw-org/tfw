package tfw.immutable.iis.objectiis;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.objectila.ObjectIla;
import tfw.immutable.ila.objectila.ObjectIlaFromArray;

class ObjectIisFactoryFromObjectIlaTest {
    @Test
    void testArguments() {
        assertThrows(IllegalArgumentException.class, () -> ObjectIisFactoryFromObjectIla.create(null));
    }

    @Test
    void testFactory() throws IOException {
        final Object[] expectedArray = new Object[11];
        final ObjectIla<Object> ila = ObjectIlaFromArray.create(expectedArray);
        final ObjectIisFactory<Object> iisf = ObjectIisFactoryFromObjectIla.create(ila);
        final ObjectIis<Object> iis = iisf.create();
        final Object[] actualArray = new Object[expectedArray.length];

        assertEquals(actualArray.length, iis.read(actualArray, 0, actualArray.length));
        assertArrayEquals(expectedArray, actualArray);
    }
}
// AUTO GENERATED FROM TEMPLATE
