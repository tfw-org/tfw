package tfw.immutable.ila.stringila;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import tfw.immutable.DataInvalidException;

class StringIlaIteratorTest {
    @Test
    void testStringIlaFill() throws DataInvalidException {
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
            assertEquals(ii.next(), array[i], "element " + i + " not equal!");
        }

        if (i != array.length) {
            fail("Iterator stopped at " + i + " not " + array.length);
        }
    }
}
