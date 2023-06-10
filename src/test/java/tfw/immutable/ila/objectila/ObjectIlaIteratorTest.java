package tfw.immutable.ila.objectila;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import tfw.immutable.DataInvalidException;

class ObjectIlaIteratorTest {
    @Test
    void testObjectIlaFill() throws DataInvalidException {
        final int LENGTH = 29;
        Object[] array = new Object[LENGTH];

        for (int i = 0; i < array.length; i++) {
            array[i] = new Object();
        }

        ObjectIla ila = ObjectIlaFromArray.create(array);
        ObjectIlaIterator ii = new ObjectIlaIterator(ila);

        int i = 0;
        for (; ii.hasNext(); i++) {
            if (i == array.length) {
                fail("Iterator did not stop correctly");
            }
            assertEquals(ii.next(), array[i], "element " + i + " not equal!");
        }

        if (i != array.length) {
            fail("Iterator stopped at " + i + " not " + array.length);
        }
    }
}
