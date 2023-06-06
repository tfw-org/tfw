package tfw.immutable.ila.objectila;

import junit.framework.TestCase;
import tfw.immutable.DataInvalidException;

public class ObjectIlaIteratorTest extends TestCase {
    public void testObjectIlaFill() throws DataInvalidException {
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
            assertEquals("element " + i + " not equal!", ii.next(), array[i]);
        }

        if (i != array.length) {
            fail("Iterator stopped at " + i + " not " + array.length);
        }
    }
}
