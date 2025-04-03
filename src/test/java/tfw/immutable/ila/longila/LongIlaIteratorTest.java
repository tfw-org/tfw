package tfw.immutable.ila.longila;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.Random;
import org.junit.jupiter.api.Test;

final class LongIlaIteratorTest {
    @Test
    void longIlaIteratorTest() throws IOException {
        final Random random = new Random();
        final int LENGTH = 29;
        long[] array = new long[LENGTH];

        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextLong();
        }

        LongIla ila = LongIlaFromArray.create(array);
        LongIlaIterator ii = new LongIlaIterator(ila, new long[100]);

        int i = 0;
        for (; ii.hasNext(); i++) {
            assertThat(i).isNotEqualTo(array.length);
            assertThat(ii.next()).isEqualTo(array[i]);
        }

        assertThat(i).isEqualTo(array.length);
    }
}
