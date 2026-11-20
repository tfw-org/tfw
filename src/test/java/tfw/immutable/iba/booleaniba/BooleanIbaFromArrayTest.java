package tfw.immutable.iba.booleaniba;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigInteger;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

class BooleanIbaFromArrayTest {
    private static final BigInteger TWO = BigInteger.valueOf(2);
    private static final BigInteger THREE = BigInteger.valueOf(3);
    private static final BigInteger FOUR = BigInteger.valueOf(4);

    @Test
    void parameterTest() {
        assertThatThrownBy(() -> BooleanIbaFromArray.create(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("array (=null) == null not allowed!");
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

    @Test
    void boundaryTest() throws Exception {
        final boolean[] source = {true, true, true};
        final boolean[] destination = new boolean[3];

        try (BooleanIba iba = BooleanIbaFromArray.create(source)) {
            iba.get(destination, 0, BigInteger.ZERO, 3);
            assertThat(destination).isEqualTo(source);

            // An empty range at the exact end of either array is valid.
            iba.get(destination, destination.length, THREE, 0);
            iba.get(destination, 0, THREE, 0);

            // Empty ranges beyond either end are invalid.
            assertThatThrownBy(() -> iba.get(destination, destination.length + 1, THREE, 0))
                    .isInstanceOf(IllegalArgumentException.class);
            assertThatThrownBy(() -> iba.get(destination, 0, FOUR, 0)).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void invalidRangeTest() throws Exception {
        final boolean[] source = new boolean[3];
        final boolean[] destination = new boolean[3];

        try (BooleanIba iba = BooleanIbaFromArray.create(source)) {
            assertThatThrownBy(() -> iba.get(destination, -1, BigInteger.ZERO, 1))
                    .isInstanceOf(IllegalArgumentException.class);
            assertThatThrownBy(() -> iba.get(destination, 0, BigInteger.ZERO, -1))
                    .isInstanceOf(IllegalArgumentException.class);
            assertThatThrownBy(() -> iba.get(destination, 2, BigInteger.ZERO, 2))
                    .isInstanceOf(IllegalArgumentException.class);
            assertThatThrownBy(() -> iba.get(destination, 0, TWO, 2)).isInstanceOf(IllegalArgumentException.class);
            assertThatThrownBy(() -> iba.get(null, 0, BigInteger.ZERO, 0)).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void closedTest() throws Exception {
        final boolean[] source = new boolean[1];
        final boolean[] destination = new boolean[1];

        final BooleanIba iba = BooleanIbaFromArray.create(source);
        iba.close();

        assertThatThrownBy(() -> iba.get(destination, 0, BigInteger.ZERO, 0))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("This object is closed!");
    }
}
// AUTO GENERATED FROM TEMPLATE
