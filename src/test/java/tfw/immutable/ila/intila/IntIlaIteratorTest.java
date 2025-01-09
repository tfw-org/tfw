package tfw.immutable.ila.intila;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.Random;
import org.junit.jupiter.api.Test;

final class IntIlaIteratorTest {
    @Test
    void intIlaIteratorTest() throws IOException {
        final Random random = new Random();
        final int LENGTH = 29;
        int[] array = new int[LENGTH];

        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt();
        }

        IntIla ila = IntIlaFromArray.create(array);
        IntIlaIterator ii = new IntIlaIterator(ila, new int[100]);

        int i = 0;
        for (; ii.hasNext(); i++) {
            assertThat(i).isNotEqualTo(array.length);
            assertThat(ii.next()).isEqualTo(array[i]);
        }

        assertThat(i).isEqualTo(array.length);
    }
}
