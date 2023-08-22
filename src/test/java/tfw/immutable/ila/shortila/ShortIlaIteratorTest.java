package tfw.immutable.ila.shortila;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.Random;
import org.junit.jupiter.api.Test;

class ShortIlaIteratorTest {
    @Test
    void testShortIlaFill() throws IOException {
        final Random random = new Random();
        final int LENGTH = 29;
        short[] array = new short[LENGTH];

        for (int i = 0; i < array.length; i++) {
            array[i] = (short) random.nextInt();
        }

        ShortIla ila = ShortIlaFromArray.create(array);
        ShortIlaIterator ii = new ShortIlaIterator(ila, new short[100]);

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
