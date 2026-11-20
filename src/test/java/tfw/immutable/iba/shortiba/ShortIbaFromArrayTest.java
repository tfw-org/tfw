package tfw.immutable.iba.shortiba;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigInteger;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

class ShortIbaFromArrayTest {
    private static final BigInteger TWO = BigInteger.valueOf(2);
    private static final BigInteger THREE = BigInteger.valueOf(3);
    private static final BigInteger FOUR = BigInteger.valueOf(4);

    @Test
    void parameterTest() {
        assertThatThrownBy(() -> ShortIbaFromArray.create(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("array (=null) == null not allowed!");
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

    @Test
    void boundaryTest() throws Exception {
        final short[] source = {(short) 1, (short) 1, (short) 1};
        final short[] destination = new short[3];

        try (ShortIba iba = ShortIbaFromArray.create(source)) {
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
        final short[] source = new short[3];
        final short[] destination = new short[3];

        try (ShortIba iba = ShortIbaFromArray.create(source)) {
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
        final short[] source = new short[1];
        final short[] destination = new short[1];

        final ShortIba iba = ShortIbaFromArray.create(source);
        iba.close();

        assertThatThrownBy(() -> iba.get(destination, 0, BigInteger.ZERO, 0))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("This object is closed!");
    }
}
// AUTO GENERATED FROM TEMPLATE
