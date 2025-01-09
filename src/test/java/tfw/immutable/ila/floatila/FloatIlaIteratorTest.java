package tfw.immutable.ila.floatila;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.Random;
import org.junit.jupiter.api.Test;

final class FloatIlaIteratorTest {
    @Test
    void floatIlaIteratorTest() throws IOException {
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
            assertThat(i).isNotEqualTo(array.length);
            assertThat(ii.next()).isEqualTo(array[i]);
        }

        assertThat(i).isEqualTo(array.length);
    }
}
