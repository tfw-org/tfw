package tfw.immutable.iba.shortiba;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigInteger;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

class ShortIbaFromArrayTest {
    @Test
    void parameterTest() {
        assertThatThrownBy(() -> ShortIbaFromArray.create(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("array == null not allowed!");
    }

    @Test
    void validTest() throws Exception {
        final short[] expectedArray = new short[10];
        final short[] actualArray = expectedArray.clone();

        Arrays.fill(actualArray, (short) 0);
        Arrays.fill(expectedArray, (short) 1);

        try (ShortIba iba = ShortIbaFromArray.create(expectedArray)) {
            assertThat(iba.length()).isEqualTo(BigInteger.TEN);

            iba.get(actualArray, 0, BigInteger.ZERO, actualArray.length);

            assertThat(actualArray).isEqualTo(expectedArray);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
