package tfw.immutable.iis.intiis;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.intila.IntIla;
import tfw.immutable.ila.intila.IntIlaFromArray;

final class IntIisFromIntIlaTest {
    @Test
    void argumentsTest() {
        assertThatThrownBy(() -> IntIisFromIntIla.create(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void readTest() throws IOException {
        final int[] expectedArray = new int[12];
        final IntIla ila = IntIlaFromArray.create(expectedArray);

        try (IntIis iis = IntIisFromIntIla.create(ila)) {
            final int[] actualArray = new int[expectedArray.length];

            for (int i = 0; i < actualArray.length; i += actualArray.length / 4) {
                assertThat(iis.read(actualArray, i, actualArray.length / 4)).isEqualTo(actualArray.length / 4);
            }

            assertThat(iis.read(new int[1], 0, 1)).isEqualTo(-1);
            assertThat(actualArray).isEqualTo(expectedArray);
        }
    }

    @Test
    void read2Test() throws IOException {
        final int[] array = new int[12];
        final int[] expectedArray = new int[array.length];

        Arrays.fill(array, 1);
        Arrays.fill(expectedArray, 0, expectedArray.length, 0);
        Arrays.fill(expectedArray, 0, expectedArray.length - 1, 1);

        final IntIla ila = IntIlaFromArray.create(array);

        try (IntIis iis = IntIisFromIntIla.create(ila)) {
            final int[] actualArray = new int[expectedArray.length];

            Arrays.fill(actualArray, 0);

            assertThat(iis.skip(1)).isEqualTo(1);
            assertThat(iis.read(actualArray, 0, actualArray.length)).isEqualTo(expectedArray.length - 1);
            assertThat(actualArray).isEqualTo(expectedArray);
        }
    }

    @Test
    void skipTest() throws IOException {
        final int[] expectedArray = new int[12];
        final IntIla ila = IntIlaFromArray.create(expectedArray);

        try (IntIis iis = IntIisFromIntIla.create(ila)) {
            for (int i = 0; i < 4; i++) {
                assertThat(iis.skip(expectedArray.length / 4)).isEqualTo(expectedArray.length / 4);
            }

            assertThat(iis.skip(1)).isEqualTo(-1);
            assertThat(iis.skip(1)).isEqualTo(-1);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
