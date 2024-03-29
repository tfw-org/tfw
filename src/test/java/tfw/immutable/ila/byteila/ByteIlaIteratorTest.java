package tfw.immutable.ila.byteila;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.Random;
import org.junit.jupiter.api.Test;

class ByteIlaIteratorTest {
    @Test
    void testByteIlaFill() throws IOException {
        final Random random = new Random();
        final int LENGTH = 29;
        byte[] array = new byte[LENGTH];

        for (int i = 0; i < array.length; i++) {
            array[i] = (byte) random.nextInt();
        }

        ByteIla ila = ByteIlaFromArray.create(array);
        ByteIlaIterator ii = new ByteIlaIterator(ila, new byte[100]);

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
