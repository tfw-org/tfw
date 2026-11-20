package tfw.immutable.iba;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static tfw.immutable.iba.bitiba.BitIba.MAX_BITS_IN_ARRAY;

import java.math.BigInteger;
import org.junit.jupiter.api.Test;

class ImmutableBigIntegerArrayUtilTest {
    @Test
    void validateArrayParametersTest() {
        assertThatCode(() -> ImmutableBigIntegerArrayUtil.validateGetParameters(
                        3, 3, BigInteger.valueOf(3), BigInteger.valueOf(3), 0))
                .doesNotThrowAnyException();

        assertThatThrownBy(() ->
                        ImmutableBigIntegerArrayUtil.validateGetParameters(-1, 0, BigInteger.ZERO, BigInteger.ZERO, 0))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> ImmutableBigIntegerArrayUtil.validateGetParameters(
                        3, 4, BigInteger.ZERO, BigInteger.valueOf(3), 0))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> ImmutableBigIntegerArrayUtil.validateGetParameters(
                        3, 0, BigInteger.valueOf(4), BigInteger.valueOf(3), 0))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> ImmutableBigIntegerArrayUtil.validateGetParameters(
                        3, 0, BigInteger.ZERO, BigInteger.valueOf(3), 4))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> ImmutableBigIntegerArrayUtil.validateGetParameters(
                        3, 0, BigInteger.ZERO, BigInteger.valueOf(3), -1))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void validateBitArrayParametersTest() {
        assertThatCode(() -> ImmutableBigIntegerArrayUtil.validateGetParameters(
                        1, (long) Long.SIZE, BigInteger.valueOf(64), BigInteger.valueOf(64), 0L))
                .doesNotThrowAnyException();

        assertThatThrownBy(() -> ImmutableBigIntegerArrayUtil.validateGetParameters(
                        -1, 0L, BigInteger.ZERO, BigInteger.ZERO, 0L))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> ImmutableBigIntegerArrayUtil.validateGetParameters(
                        1, Long.SIZE + 1L, BigInteger.ZERO, BigInteger.valueOf(64), 0L))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> ImmutableBigIntegerArrayUtil.validateGetParameters(
                        1, 0L, BigInteger.valueOf(65), BigInteger.valueOf(64), 0L))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> ImmutableBigIntegerArrayUtil.validateGetParameters(
                        1, 0L, BigInteger.ZERO, BigInteger.valueOf(64), 65L))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> ImmutableBigIntegerArrayUtil.validateGetParameters(
                        1, 0L, BigInteger.ZERO, BigInteger.valueOf(64), -1L))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void maximumBitArrayOffsetTest() {
        final int maximumArrayLength = Integer.MAX_VALUE - 8;

        assertThatCode(() -> ImmutableBigIntegerArrayUtil.validateGetParameters(
                        maximumArrayLength, MAX_BITS_IN_ARRAY, BigInteger.ZERO, BigInteger.ZERO, 0))
                .doesNotThrowAnyException();

        assertThatThrownBy(() -> ImmutableBigIntegerArrayUtil.validateGetParameters(
                        Integer.MAX_VALUE, MAX_BITS_IN_ARRAY + 1, BigInteger.ZERO, BigInteger.ZERO, 0))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
