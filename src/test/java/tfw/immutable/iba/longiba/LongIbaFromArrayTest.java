package tfw.immutable.iba.longiba;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigInteger;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

class LongIbaFromArrayTest {
    @Test
    void parameterTest() {
        assertThatThrownBy(() -> LongIbaFromArray.create(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("array == null not allowed!");
    }

    @Test
    void validTest() throws Exception {
        final long[] expectedArray = new long[10];
        final long[] actualArray = expectedArray.clone();

        Arrays.fill(actualArray, 0L);
        Arrays.fill(expectedArray, 1L);

        try (LongIba iba = LongIbaFromArray.create(expectedArray)) {
            assertThat(iba.length()).isEqualTo(BigInteger.TEN);

            iba.get(actualArray, 0, BigInteger.ZERO, actualArray.length);

            assertThat(actualArray).isEqualTo(expectedArray);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
