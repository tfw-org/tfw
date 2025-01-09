package tfw.immutable.ila.objectila;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import org.junit.jupiter.api.Test;

final class ObjectIlaIteratorTest {
    @Test
    void objectIlaIteratorTest() throws IOException {
        final int LENGTH = 29;
        Object[] array = new Object[LENGTH];

        for (int i = 0; i < array.length; i++) {
            array[i] = new Object();
        }

        ObjectIla<Object> ila = ObjectIlaFromArray.create(array);
        ObjectIlaIterator<Object> ii = new ObjectIlaIterator<>(ila, new Object[100]);

        int i = 0;
        for (; ii.hasNext(); i++) {
            assertThat(i).isNotEqualTo(array.length);
            assertThat(ii.next()).isEqualTo(array[i]);
        }

        assertThat(i).isEqualTo(array.length);
    }
}
