package tfw.immutable.ila.shortila;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.Random;
import org.junit.jupiter.api.Test;

final class ShortIlaIteratorTest {
    @Test
    void shortIlaIteratorTest() throws IOException {
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
            assertThat(i).isNotEqualTo(array.length);
            assertThat(ii.next()).isEqualTo(array[i]);
        }

        assertThat(i).isEqualTo(array.length);
    }
}
