package tfw.immutable.ila.floatila;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.DataInvalidException;

class FloatIlaIteratorTest {
    @Test
    void testFloatIlaFill() throws DataInvalidException {
        final Random random = new Random();
        final int LENGTH = 29;
        float[] array = new float[LENGTH];

        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextFloat();
        }

        FloatIla ila = FloatIlaFromArray.create(array);
        FloatIlaIterator ii = new FloatIlaIterator(ila, new float[100]);

        int i = 0;
        for (; ii.hasNext(); i++) {
            if (i == array.length) {
                fail("Iterator did not stop correctly");
            }
            assertEquals(ii.next(), array[i], 0f, "element " + i + " not equal!");
        }

        if (i != array.length) {
            fail("Iterator stopped at " + i + " not " + array.length);
        }
    }
}
