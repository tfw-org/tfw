package tfw.immutable.iis.booleaniis;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.booleanila.BooleanIla;
import tfw.immutable.ila.booleanila.BooleanIlaFromArray;

final class BooleanIisFromBooleanIlaTest {
    @Test
    void argumentsTest() {
        assertThatThrownBy(() -> BooleanIisFromBooleanIla.create(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void readTest() throws IOException {
        final boolean[] expectedArray = new boolean[12];
        final BooleanIla ila = BooleanIlaFromArray.create(expectedArray);

        try (BooleanIis iis = BooleanIisFromBooleanIla.create(ila)) {
            final boolean[] actualArray = new boolean[expectedArray.length];

            for (int i = 0; i < actualArray.length; i += actualArray.length / 4) {
                assertThat(iis.read(actualArray, i, actualArray.length / 4)).isEqualTo(actualArray.length / 4);
            }

            assertThat(iis.read(new boolean[1], 0, 1)).isEqualTo(-1);
            assertThat(actualArray).isEqualTo(expectedArray);
        }
    }

    @Test
    void read2Test() throws IOException {
        final boolean[] array = new boolean[12];
        final boolean[] expectedArray = new boolean[array.length];

        Arrays.fill(array, true);
        Arrays.fill(expectedArray, 0, expectedArray.length, false);
        Arrays.fill(expectedArray, 0, expectedArray.length - 1, true);

        final BooleanIla ila = BooleanIlaFromArray.create(array);

        try (BooleanIis iis = BooleanIisFromBooleanIla.create(ila)) {
            final boolean[] actualArray = new boolean[expectedArray.length];

            Arrays.fill(actualArray, false);

            assertThat(iis.skip(1)).isEqualTo(1);
            assertThat(iis.read(actualArray, 0, actualArray.length)).isEqualTo(expectedArray.length - 1);
            assertThat(actualArray).isEqualTo(expectedArray);
        }
    }

    @Test
    void skipTest() throws IOException {
        final boolean[] expectedArray = new boolean[12];
        final BooleanIla ila = BooleanIlaFromArray.create(expectedArray);

        try (BooleanIis iis = BooleanIisFromBooleanIla.create(ila)) {
            for (int i = 0; i < 4; i++) {
                assertThat(iis.skip(expectedArray.length / 4)).isEqualTo(expectedArray.length / 4);
            }

            assertThat(iis.skip(1)).isEqualTo(-1);
            assertThat(iis.skip(1)).isEqualTo(-1);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
