package tfw.immutable.ila.doubleila;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.Random;
import org.junit.jupiter.api.Test;

class DoubleIlaIteratorTest {
    @Test
    void testDoubleIlaFill() throws IOException {
        final Random random = new Random();
        final int LENGTH = 29;
        double[] array = new double[LENGTH];

        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextDouble();
        }

        DoubleIla ila = DoubleIlaFromArray.create(array);
        DoubleIlaIterator ii = new DoubleIlaIterator(ila, new double[100]);

        int i = 0;
        for (; ii.hasNext(); i++) {
            if (i == array.length) {
                fail("Iterator did not stop correctly");
            }
            assertEquals(ii.next(), array[i], 0, "element " + i + " not equal!");
        }

        if (i != array.length) {
            fail("Iterator stopped at " + i + " not " + array.length);
        }
    }
}
