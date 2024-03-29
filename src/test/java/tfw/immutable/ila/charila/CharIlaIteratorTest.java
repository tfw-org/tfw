package tfw.immutable.ila.charila;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.Random;
import org.junit.jupiter.api.Test;

class CharIlaIteratorTest {
    @Test
    void testCharIlaFill() throws IOException {
        final Random random = new Random();
        final int LENGTH = 29;
        char[] array = new char[LENGTH];

        for (int i = 0; i < array.length; i++) {
            array[i] = (char) random.nextInt();
        }

        CharIla ila = CharIlaFromArray.create(array);
        CharIlaIterator ii = new CharIlaIterator(ila, new char[100]);

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
