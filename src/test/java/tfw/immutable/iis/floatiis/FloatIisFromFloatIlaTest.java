package tfw.immutable.iis.floatiis;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.floatila.FloatIla;
import tfw.immutable.ila.floatila.FloatIlaFromArray;

final class FloatIisFromFloatIlaTest {
    @Test
    void argumentsTest() {
        assertThatThrownBy(() -> FloatIisFromFloatIla.create(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void readTest() throws IOException {
        final float[] expectedArray = new float[12];
        final FloatIla ila = FloatIlaFromArray.create(expectedArray);

        try (FloatIis iis = FloatIisFromFloatIla.create(ila)) {
            final float[] actualArray = new float[expectedArray.length];

            for (int i = 0; i < actualArray.length; i += actualArray.length / 4) {
                assertThat(iis.read(actualArray, i, actualArray.length / 4)).isEqualTo(actualArray.length / 4);
            }

            assertThat(iis.read(new float[1], 0, 1)).isEqualTo(-1);
            assertThat(actualArray).isEqualTo(expectedArray);
        }
    }

    @Test
    void read2Test() throws IOException {
        final float[] array = new float[12];
        final float[] expectedArray = new float[array.length];

        Arrays.fill(array, 1.0f);
        Arrays.fill(expectedArray, 0, expectedArray.length, 0.0f);
        Arrays.fill(expectedArray, 0, expectedArray.length - 1, 1.0f);

        final FloatIla ila = FloatIlaFromArray.create(array);

        try (FloatIis iis = FloatIisFromFloatIla.create(ila)) {
            final float[] actualArray = new float[expectedArray.length];

            Arrays.fill(actualArray, 0.0f);

            assertThat(iis.skip(1)).isEqualTo(1);
            assertThat(iis.read(actualArray, 0, actualArray.length)).isEqualTo(expectedArray.length - 1);
            assertThat(actualArray).isEqualTo(expectedArray);
        }
    }

    @Test
    void skipTest() throws IOException {
        final float[] expectedArray = new float[12];
        final FloatIla ila = FloatIlaFromArray.create(expectedArray);

        try (FloatIis iis = FloatIisFromFloatIla.create(ila)) {
            for (int i = 0; i < 4; i++) {
                assertThat(iis.skip(expectedArray.length / 4)).isEqualTo(expectedArray.length / 4);
            }

            assertThat(iis.skip(1)).isEqualTo(-1);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
