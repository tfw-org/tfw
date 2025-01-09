package tfw.immutable.ila.charila;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.Random;
import org.junit.jupiter.api.Test;

final class CharIlaIteratorTest {
    @Test
    void charIlaFillTest() throws IOException {
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
            assertThat(i).isNotEqualTo(array.length);
            assertThat(ii.next()).isEqualTo(array[i]);
        }

        assertThat(i).isEqualTo(array.length);
    }
}
