package tfw.immutable.iba.objectiba;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigInteger;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

class ObjectIbaFromArrayTest {
    @Test
    void parameterTest() {
        assertThatThrownBy(() -> ObjectIbaFromArray.create(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("array == null not allowed!");
    }

    @Test
    void validTest() throws Exception {
        final Object[] expectedArray = new Object[10];
        final Object[] actualArray = expectedArray.clone();

        Arrays.fill(actualArray, Object.class);
        Arrays.fill(expectedArray, String.class);

        try (ObjectIba iba = ObjectIbaFromArray.create(expectedArray)) {
            assertThat(iba.length()).isEqualTo(BigInteger.TEN);

            iba.get(actualArray, 0, BigInteger.ZERO, actualArray.length);

            assertThat(actualArray).isEqualTo(expectedArray);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
