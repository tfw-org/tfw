package tfw.immutable.iis.doubleiis;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.doubleila.DoubleIlaFromArray;

final class DoubleIisFromDoubleIlaTest {
    @Test
    void argumentsTest() {
        assertThatThrownBy(() -> DoubleIisFromDoubleIla.create(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void readTest() throws IOException {
        final double[] expectedArray = new double[12];
        final DoubleIla ila = DoubleIlaFromArray.create(expectedArray);

        try (DoubleIis iis = DoubleIisFromDoubleIla.create(ila)) {
            final double[] actualArray = new double[expectedArray.length];

            for (int i = 0; i < actualArray.length; i += actualArray.length / 4) {
                assertThat(iis.read(actualArray, i, actualArray.length / 4)).isEqualTo(actualArray.length / 4);
            }

            assertThat(iis.read(new double[1], 0, 1)).isEqualTo(-1);
            assertThat(actualArray).isEqualTo(expectedArray);
        }
    }

    @Test
    void read2Test() throws IOException {
        final double[] array = new double[12];
        final double[] expectedArray = new double[array.length];

        Arrays.fill(array, 1.0);
        Arrays.fill(expectedArray, 0, expectedArray.length, 0.0);
        Arrays.fill(expectedArray, 0, expectedArray.length - 1, 1.0);

        final DoubleIla ila = DoubleIlaFromArray.create(array);

        try (DoubleIis iis = DoubleIisFromDoubleIla.create(ila)) {
            final double[] actualArray = new double[expectedArray.length];

            Arrays.fill(actualArray, 0.0);

            assertThat(iis.skip(1)).isEqualTo(1);
            assertThat(iis.read(actualArray, 0, actualArray.length)).isEqualTo(expectedArray.length - 1);
            assertThat(actualArray).isEqualTo(expectedArray);
        }
    }

    @Test
    void skipTest() throws IOException {
        final double[] expectedArray = new double[12];
        final DoubleIla ila = DoubleIlaFromArray.create(expectedArray);

        try (DoubleIis iis = DoubleIisFromDoubleIla.create(ila)) {
            for (int i = 0; i < 4; i++) {
                assertThat(iis.skip(expectedArray.length / 4)).isEqualTo(expectedArray.length / 4);
            }

            assertThat(iis.skip(1)).isEqualTo(-1);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
