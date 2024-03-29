package tfw.immutable.ila.intila;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.Random;
import org.junit.jupiter.api.Test;

class IntIlaIteratorTest {
    @Test
    void testIntIlaFill() throws IOException {
        final Random random = new Random();
        final int LENGTH = 29;
        int[] array = new int[LENGTH];

        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt();
        }

        IntIla ila = IntIlaFromArray.create(array);
        IntIlaIterator ii = new IntIlaIterator(ila, new int[100]);

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
