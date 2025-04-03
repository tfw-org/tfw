package tfw.immutable.ila.booleanila;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.Random;
import org.junit.jupiter.api.Test;

final class BooleanIlaIteratorTest {
    @Test
    void booleanIlaIteratorTest() throws IOException {
        final Random random = new Random(0);
        final int LENGTH = 29;
        boolean[] array = new boolean[LENGTH];

        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextBoolean();
        }

        BooleanIla ila = BooleanIlaFromArray.create(array);
        BooleanIlaIterator ii = new BooleanIlaIterator(ila, new boolean[100]);

        int i = 0;
        for (; ii.hasNext(); i++) {
            assertThat(i).isNotEqualTo(array.length);
            assertThat(ii.next()).isEqualTo(array[i]);
        }

        assertThat(i).isEqualTo(array.length);
    }
}
