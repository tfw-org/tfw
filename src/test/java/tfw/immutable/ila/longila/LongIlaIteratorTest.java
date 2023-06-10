package tfw.immutable.ila.longila;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.DataInvalidException;

class LongIlaIteratorTest {
    @Test
    void testLongIlaFill() throws DataInvalidException {
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
            assertEquals(ii.next(), array[i], "element " + i + " not equal!");
        }

        if (i != array.length) {
            fail("Iterator stopped at " + i + " not " + array.length);
        }
    }
}
