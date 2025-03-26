package tfw.immutable.iba.doubleiba;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigInteger;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

class DoubleIbaFromArrayTest {
    @Test
    void parameterTest() {
        assertThatThrownBy(() -> DoubleIbaFromArray.create(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("array == null not allowed!");
    }

    @Test
    void validTest() throws Exception {
        final double[] expectedArray = new double[10];
        final double[] actualArray = expectedArray.clone();

        Arrays.fill(actualArray, 0.0);
        Arrays.fill(expectedArray, 1.0);

        try (DoubleIba iba = DoubleIbaFromArray.create(expectedArray)) {
            assertThat(iba.length()).isEqualTo(BigInteger.TEN);

            iba.get(actualArray, 0, BigInteger.ZERO, actualArray.length);

            assertThat(actualArray).isEqualTo(expectedArray);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
