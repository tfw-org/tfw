package tfw.immutable.iis.objectiis;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.objectila.ObjectIla;
import tfw.immutable.ila.objectila.ObjectIlaFromArray;

final class ObjectIisFromObjectIlaTest {
    @Test
    void argumentsTest() {
        assertThatThrownBy(() -> ObjectIisFromObjectIla.create(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void readTest() throws IOException {
        final Object[] expectedArray = new Object[12];
        final ObjectIla<Object> ila = ObjectIlaFromArray.create(expectedArray);

        try (ObjectIis<Object> iis = ObjectIisFromObjectIla.create(ila)) {
            final Object[] actualArray = new Object[expectedArray.length];

            for (int i = 0; i < actualArray.length; i += actualArray.length / 4) {
                assertThat(iis.read(actualArray, i, actualArray.length / 4)).isEqualTo(actualArray.length / 4);
            }

            assertThat(iis.read(new Object[1], 0, 1)).isEqualTo(-1);
            assertThat(actualArray).isEqualTo(expectedArray);
        }
    }

    @Test
    void read2Test() throws IOException {
        final Object[] array = new Object[12];
        final Object[] expectedArray = new Object[array.length];

        Arrays.fill(array, String.class);
        Arrays.fill(expectedArray, 0, expectedArray.length, Object.class);
        Arrays.fill(expectedArray, 0, expectedArray.length - 1, String.class);

        final ObjectIla<Object> ila = ObjectIlaFromArray.create(array);

        try (ObjectIis<Object> iis = ObjectIisFromObjectIla.create(ila)) {
            final Object[] actualArray = new Object[expectedArray.length];

            Arrays.fill(actualArray, Object.class);

            assertThat(iis.skip(1)).isEqualTo(1);
            assertThat(iis.read(actualArray, 0, actualArray.length)).isEqualTo(expectedArray.length - 1);
            assertThat(actualArray).isEqualTo(expectedArray);
        }
    }

    @Test
    void skipTest() throws IOException {
        final Object[] expectedArray = new Object[12];
        final ObjectIla<Object> ila = ObjectIlaFromArray.create(expectedArray);

        try (ObjectIis<Object> iis = ObjectIisFromObjectIla.create(ila)) {
            for (int i = 0; i < 4; i++) {
                assertThat(iis.skip(expectedArray.length / 4)).isEqualTo(expectedArray.length / 4);
            }

            assertThat(iis.skip(1)).isEqualTo(-1);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
