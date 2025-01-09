package tfw.immutable.ila.doubleila;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.Random;
import org.junit.jupiter.api.Test;

final class DoubleIlaIteratorTest {
    @Test
    void doubleIlaIteratorTest() throws IOException {
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
            assertThat(i).isNotEqualTo(array.length);
            assertThat(ii.next()).isEqualTo(array[i]);
        }

        assertThat(i).isEqualTo(array.length);
    }
}
