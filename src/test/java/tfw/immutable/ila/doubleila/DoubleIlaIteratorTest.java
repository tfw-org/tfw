package tfw.immutable.ila.doubleila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.DataInvalidException;

public class DoubleIlaIteratorTest extends TestCase {
    public void testDoubleIlaFill() throws DataInvalidException {
        final Random random = new Random();
        final int LENGTH = 29;
        double[] array = new double[LENGTH];

        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextDouble();
        }

        DoubleIla ila = DoubleIlaFromArray.create(array);
        DoubleIlaIterator ii = new DoubleIlaIterator(ila);

        int i = 0;
        for (; ii.hasNext(); i++) {
            if (i == array.length) {
                fail("Iterator did not stop correctly");
            }
            assertEquals("element " + i + " not equal!", ii.next(), array[i], 0);
        }

        if (i != array.length) {
            fail("Iterator stopped at " + i + " not " + array.length);
        }
    }
}
