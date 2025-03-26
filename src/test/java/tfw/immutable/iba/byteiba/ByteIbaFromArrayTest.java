package tfw.immutable.iba.byteiba;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigInteger;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

class ByteIbaFromArrayTest {
    @Test
    void parameterTest() {
        assertThatThrownBy(() -> ByteIbaFromArray.create(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("array == null not allowed!");
    }

    @Test
    void validTest() throws Exception {
        final byte[] expectedArray = new byte[10];
        final byte[] actualArray = expectedArray.clone();

        Arrays.fill(actualArray, (byte) 0);
        Arrays.fill(expectedArray, (byte) 1);

        try (ByteIba iba = ByteIbaFromArray.create(expectedArray)) {
            assertThat(iba.length()).isEqualTo(BigInteger.TEN);

            iba.get(actualArray, 0, BigInteger.ZERO, actualArray.length);

            assertThat(actualArray).isEqualTo(expectedArray);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
