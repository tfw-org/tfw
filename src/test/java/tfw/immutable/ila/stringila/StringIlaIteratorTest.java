package tfw.immutable.ila.stringila;

import junit.framework.TestCase;
import tfw.immutable.DataInvalidException;

public class StringIlaIteratorTest extends TestCase {
    public void testStringIlaFill() throws DataInvalidException {
        final int LENGTH = 29;
        String[] array = new String[LENGTH];

        for (int i = 0; i < array.length; i++) {
            array[i] = new String();
        }

        StringIla ila = StringIlaFromArray.create(array);
        StringIlaIterator ii = new StringIlaIterator(ila);

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
