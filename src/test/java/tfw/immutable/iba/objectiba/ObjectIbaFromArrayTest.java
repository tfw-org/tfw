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
                .hasMessage("array (=null) == null not allowed!");
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

    @Test
    void boundaryTest() throws Exception {
        final Object[] source = new Object[] {String.class, String.class, String.class};
        final Object[] destination = new Object[3];

        try (ObjectIba iba = ObjectIbaFromArray.create(source)) {
            iba.get(destination, 0, BigInteger.ZERO, 3);
            assertThat(destination).isEqualTo(source);

            // An empty range at the exact end of either array is valid.
            iba.get(destination, destination.length, BigInteger.valueOf(3), 0);
            iba.get(destination, 0, BigInteger.valueOf(3), 0);

            // Empty ranges beyond either end are invalid.
            assertThatThrownBy(() -> iba.get(destination, destination.length + 1, BigInteger.valueOf(3), 0))
                    .isInstanceOf(IllegalArgumentException.class);
            assertThatThrownBy(() -> iba.get(destination, 0, BigInteger.valueOf(4), 0))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void invalidRangeTest() throws Exception {
        final Object[] source = new Object[3];
        final Object[] destination = new Object[3];

        try (ObjectIba iba = ObjectIbaFromArray.create(source)) {
            assertThatThrownBy(() -> iba.get(destination, -1, BigInteger.ZERO, 1))
                    .isInstanceOf(IllegalArgumentException.class);
            assertThatThrownBy(() -> iba.get(destination, 0, BigInteger.ZERO, -1))
                    .isInstanceOf(IllegalArgumentException.class);
            assertThatThrownBy(() -> iba.get(destination, 2, BigInteger.ZERO, 2))
                    .isInstanceOf(IllegalArgumentException.class);
            assertThatThrownBy(() -> iba.get(destination, 0, BigInteger.valueOf(2), 2))
                    .isInstanceOf(IllegalArgumentException.class);
            assertThatThrownBy(() -> iba.get(null, 0, BigInteger.ZERO, 0)).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void closedTest() throws Exception {
        final Object[] source = new Object[1];
        final Object[] destination = new Object[1];

        final ObjectIba iba = ObjectIbaFromArray.create(source);
        iba.close();

        assertThatThrownBy(() -> iba.get(destination, 0, BigInteger.ZERO, 0))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("This object is closed!");
    }
}
// AUTO GENERATED FROM TEMPLATE
