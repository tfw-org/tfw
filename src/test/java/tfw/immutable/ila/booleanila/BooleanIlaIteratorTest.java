package tfw.immutable.ila.booleanila;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.Random;
import org.junit.jupiter.api.Test;

class BooleanIlaIteratorTest {
    @Test
    void testBooleanIlaFill() throws IOException {
        final Random random = new Random();
        final int LENGTH = 29;
        boolean[] array = new boolean[LENGTH];

        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextBoolean();
        }

        BooleanIla ila = BooleanIlaFromArray.create(array);
        BooleanIlaIterator ii = new BooleanIlaIterator(ila, new boolean[100]);

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
