package tfw.immutable.iba.intiba;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigInteger;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

class IntIbaFromArrayTest {
    @Test
    void parameterTest() {
        assertThatThrownBy(() -> IntIbaFromArray.create(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("array == null not allowed!");
    }

    @Test
    void validTest() throws Exception {
        final int[] expectedArray = new int[10];
        final int[] actualArray = expectedArray.clone();

        Arrays.fill(actualArray, 0);
        Arrays.fill(expectedArray, 1);

        try (IntIba iba = IntIbaFromArray.create(expectedArray)) {
            assertThat(iba.length()).isEqualTo(BigInteger.TEN);

            iba.get(actualArray, 0, BigInteger.ZERO, actualArray.length);

            assertThat(actualArray).isEqualTo(expectedArray);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
