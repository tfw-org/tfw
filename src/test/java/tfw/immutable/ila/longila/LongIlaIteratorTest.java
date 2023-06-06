package tfw.immutable.ila.longila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.DataInvalidException;

public class LongIlaIteratorTest extends TestCase {
    public void testLongIlaFill() throws DataInvalidException {
        final Random random = new Random();
        final int LENGTH = 29;
        long[] array = new long[LENGTH];

        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextLong();
        }

        LongIla ila = LongIlaFromArray.create(array);
        LongIlaIterator ii = new LongIlaIterator(ila);

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
