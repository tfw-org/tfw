package tfw.immutable.ila.byteila;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.Random;
import org.junit.jupiter.api.Test;

final class ByteIlaIteratorTest {
    @Test
    void byteIlaIteratorTest() throws IOException {
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
            assertThat(i).isNotEqualTo(array.length);
            assertThat(ii.next()).isEqualTo(array[i]);
        }

        assertThat(i).isEqualTo(array.length);
    }
}
