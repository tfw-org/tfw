package tfw.immutable.iba.floatiba;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigInteger;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

class FloatIbaFromArrayTest {
    private static final BigInteger TWO = BigInteger.valueOf(2);
    private static final BigInteger THREE = BigInteger.valueOf(3);
    private static final BigInteger FOUR = BigInteger.valueOf(4);

    @Test
    void parameterTest() {
        assertThatThrownBy(() -> FloatIbaFromArray.create(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("array (=null) == null not allowed!");
    }

    @Test
    void validTest() throws Exception {
        final float[] expectedArray = new float[10];
        final float[] actualArray = expectedArray.clone();

        Arrays.fill(actualArray, 0.0f);
        Arrays.fill(expectedArray, 1.0f);

        try (FloatIba iba = FloatIbaFromArray.create(expectedArray)) {
            assertThat(iba.length()).isEqualTo(BigInteger.TEN);

            iba.get(actualArray, 0, BigInteger.ZERO, actualArray.length);

            assertThat(actualArray).isEqualTo(expectedArray);
        }
    }

    @Test
    void boundaryTest() throws Exception {
        final float[] source = {1.0f, 1.0f, 1.0f};
        final float[] destination = new float[3];

        try (FloatIba iba = FloatIbaFromArray.create(source)) {
            iba.get(destination, 0, BigInteger.ZERO, 3);
            assertThat(destination).isEqualTo(source);

            // An empty range at the exact end of either array is valid.
            iba.get(destination, destination.length, THREE, 0);
            iba.get(destination, 0, THREE, 0);

            // Empty ranges beyond either end are invalid.
            assertThatThrownBy(() -> iba.get(destination, destination.length + 1, THREE, 0))
                    .isInstanceOf(IllegalArgumentException.class);
            assertThatThrownBy(() -> iba.get(destination, 0, FOUR, 0)).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void invalidRangeTest() throws Exception {
        final float[] source = new float[3];
        final float[] destination = new float[3];

        try (FloatIba iba = FloatIbaFromArray.create(source)) {
            assertThatThrownBy(() -> iba.get(destination, -1, BigInteger.ZERO, 1))
                    .isInstanceOf(IllegalArgumentException.class);
            assertThatThrownBy(() -> iba.get(destination, 0, BigInteger.ZERO, -1))
                    .isInstanceOf(IllegalArgumentException.class);
            assertThatThrownBy(() -> iba.get(destination, 2, BigInteger.ZERO, 2))
                    .isInstanceOf(IllegalArgumentException.class);
            assertThatThrownBy(() -> iba.get(destination, 0, TWO, 2)).isInstanceOf(IllegalArgumentException.class);
            assertThatThrownBy(() -> iba.get(null, 0, BigInteger.ZERO, 0)).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void closedTest() throws Exception {
        final float[] source = new float[1];
        final float[] destination = new float[1];

        final FloatIba iba = FloatIbaFromArray.create(source);
        iba.close();

        assertThatThrownBy(() -> iba.get(destination, 0, BigInteger.ZERO, 0))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("This object is closed!");
    }
}
// AUTO GENERATED FROM TEMPLATE
