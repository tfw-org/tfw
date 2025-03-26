package tfw.immutable.iba.chariba;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigInteger;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

class CharIbaFromArrayTest {
    @Test
    void parameterTest() {
        assertThatThrownBy(() -> CharIbaFromArray.create(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("array == null not allowed!");
    }

    @Test
    void validTest() throws Exception {
        final char[] expectedArray = new char[10];
        final char[] actualArray = expectedArray.clone();

        Arrays.fill(actualArray, (char) 0);
        Arrays.fill(expectedArray, (char) 1);

        try (CharIba iba = CharIbaFromArray.create(expectedArray)) {
            assertThat(iba.length()).isEqualTo(BigInteger.TEN);

            iba.get(actualArray, 0, BigInteger.ZERO, actualArray.length);

            assertThat(actualArray).isEqualTo(expectedArray);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
