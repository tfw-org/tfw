package tfw.immutable.iba.booleaniba;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigInteger;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

class BooleanIbaFromArrayTest {
    @Test
    void parameterTest() {
        assertThatThrownBy(() -> BooleanIbaFromArray.create(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("array == null not allowed!");
    }

    @Test
    void validTest() throws Exception {
        final boolean[] expectedArray = new boolean[10];
        final boolean[] actualArray = expectedArray.clone();

        Arrays.fill(actualArray, false);
        Arrays.fill(expectedArray, true);

        try (BooleanIba iba = BooleanIbaFromArray.create(expectedArray)) {
            assertThat(iba.length()).isEqualTo(BigInteger.TEN);

            iba.get(actualArray, 0, BigInteger.ZERO, actualArray.length);

            assertThat(actualArray).isEqualTo(expectedArray);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
