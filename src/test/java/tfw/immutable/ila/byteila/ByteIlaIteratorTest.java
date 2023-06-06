package tfw.immutable.ila.byteila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.DataInvalidException;

public class ByteIlaIteratorTest extends TestCase {
    public void testByteIlaFill() throws DataInvalidException {
        final Random random = new Random();
        final int LENGTH = 29;
        byte[] array = new byte[LENGTH];

        for (int i = 0; i < array.length; i++) {
            array[i] = (byte) random.nextInt();
        }

        ByteIla ila = ByteIlaFromArray.create(array);
        ByteIlaIterator ii = new ByteIlaIterator(ila);

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
