package tfw.immutable.iba.floatiba;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigInteger;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

class FloatIbaFromArrayTest {
    @Test
    void parameterTest() {
        assertThatThrownBy(() -> FloatIbaFromArray.create(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("array == null not allowed!");
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
}
// AUTO GENERATED FROM TEMPLATE
