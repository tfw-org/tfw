package tfw.immutable.iba;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static tfw.immutable.iba.bitiba.BitIba.MAX_BITS_IN_ARRAY;

import java.math.BigInteger;
import org.junit.jupiter.api.Test;

class ImmutableBigIntegerArrayUtilTest {
    private static final BigInteger NEGATIVE_ONE = BigInteger.valueOf(-1);
    private static final BigInteger TWO = BigInteger.valueOf(2);
    private static final BigInteger THREE = BigInteger.valueOf(3);
    private static final BigInteger FOUR = BigInteger.valueOf(4);
    private static final BigInteger SIXTY_THREE = BigInteger.valueOf(63);
    private static final BigInteger SIXTY_FOUR = BigInteger.valueOf(64);
    private static final BigInteger SIXTY_FIVE = BigInteger.valueOf(65);
    private static final BigInteger MAX_BITS_IN_ARRAY_BIG_INTEGER = BigInteger.valueOf(MAX_BITS_IN_ARRAY);

    @Test
    void validateArrayParametersTest() {
        // Exact-end ranges are valid.
        assertThatCode(() -> ImmutableBigIntegerArrayUtil.validateGetParameters(3, 3, THREE, THREE, 0))
                .doesNotThrowAnyException();

        // arrayLength must be nonnegative.
        assertThatThrownBy(() ->
                        ImmutableBigIntegerArrayUtil.validateGetParameters(-1, 0, BigInteger.ZERO, BigInteger.ZERO, 0))
                .isInstanceOf(IllegalArgumentException.class);

        // arrayOffset must be nonnegative.
        assertThatThrownBy(() -> ImmutableBigIntegerArrayUtil.validateGetParameters(3, -1, BigInteger.ZERO, THREE, 0))
                .isInstanceOf(IllegalArgumentException.class);

        // length must be nonnegative.
        assertThatThrownBy(() -> ImmutableBigIntegerArrayUtil.validateGetParameters(3, 0, BigInteger.ZERO, THREE, -1))
                .isInstanceOf(IllegalArgumentException.class);

        // ibaLength must be nonnegative.
        assertThatThrownBy(() ->
                        ImmutableBigIntegerArrayUtil.validateGetParameters(3, 0, BigInteger.ZERO, NEGATIVE_ONE, 0))
                .isInstanceOf(IllegalArgumentException.class);

        // ibaStart must be nonnegative.
        assertThatThrownBy(() -> ImmutableBigIntegerArrayUtil.validateGetParameters(3, 0, NEGATIVE_ONE, THREE, 0))
                .isInstanceOf(IllegalArgumentException.class);

        // arrayOffset must not exceed arrayLength.
        assertThatThrownBy(() -> ImmutableBigIntegerArrayUtil.validateGetParameters(3, 4, BigInteger.ZERO, THREE, 0))
                .isInstanceOf(IllegalArgumentException.class);

        // length must fit in the destination array.
        assertThatThrownBy(() -> ImmutableBigIntegerArrayUtil.validateGetParameters(3, 0, BigInteger.ZERO, THREE, 4))
                .isInstanceOf(IllegalArgumentException.class);

        // ibaStart must not exceed ibaLength.
        assertThatThrownBy(() -> ImmutableBigIntegerArrayUtil.validateGetParameters(3, 0, FOUR, THREE, 0))
                .isInstanceOf(IllegalArgumentException.class);

        // length must fit in the remaining source range.
        assertThatThrownBy(() -> ImmutableBigIntegerArrayUtil.validateGetParameters(5, 0, TWO, THREE, 2))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void validateBitArrayParametersTest() {
        // Exact-end ranges are valid.
        assertThatCode(() -> ImmutableBigIntegerArrayUtil.validateGetParameters(
                        1, (long) Long.SIZE, SIXTY_FOUR, SIXTY_FOUR, 0L))
                .doesNotThrowAnyException();

        // arrayLength must be nonnegative.
        assertThatThrownBy(() -> ImmutableBigIntegerArrayUtil.validateGetParameters(
                        -1, 0L, BigInteger.ZERO, BigInteger.ZERO, 0L))
                .isInstanceOf(IllegalArgumentException.class);

        // arrayOffsetInBits must be nonnegative.
        assertThatThrownBy(() ->
                        ImmutableBigIntegerArrayUtil.validateGetParameters(1, -1L, BigInteger.ZERO, SIXTY_FOUR, 0L))
                .isInstanceOf(IllegalArgumentException.class);

        // lengthInBits must be nonnegative.
        assertThatThrownBy(() ->
                        ImmutableBigIntegerArrayUtil.validateGetParameters(1, 0L, BigInteger.ZERO, SIXTY_FOUR, -1L))
                .isInstanceOf(IllegalArgumentException.class);

        // ibaLengthInBits must be nonnegative.
        assertThatThrownBy(() ->
                        ImmutableBigIntegerArrayUtil.validateGetParameters(1, 0L, BigInteger.ZERO, NEGATIVE_ONE, 0L))
                .isInstanceOf(IllegalArgumentException.class);

        // ibaStartInBits must be nonnegative.
        assertThatThrownBy(
                        () -> ImmutableBigIntegerArrayUtil.validateGetParameters(1, 0L, NEGATIVE_ONE, SIXTY_FOUR, 0L))
                .isInstanceOf(IllegalArgumentException.class);

        // arrayOffsetInBits must not exceed MAX_BITS_IN_ARRAY.
        assertThatThrownBy(() -> ImmutableBigIntegerArrayUtil.validateGetParameters(
                        Integer.MAX_VALUE - 8, MAX_BITS_IN_ARRAY + 1L, BigInteger.ZERO, BigInteger.ZERO, 0L))
                .isInstanceOf(IllegalArgumentException.class);

        // lengthInBits must be strictly less than MAX_BITS_IN_ARRAY.
        assertThatThrownBy(() -> ImmutableBigIntegerArrayUtil.validateGetParameters(
                        Integer.MAX_VALUE, 0L, BigInteger.ZERO, MAX_BITS_IN_ARRAY_BIG_INTEGER, MAX_BITS_IN_ARRAY))
                .isInstanceOf(IllegalArgumentException.class);

        // arrayOffsetInBits must fit in the destination array.
        assertThatThrownBy(() -> ImmutableBigIntegerArrayUtil.validateGetParameters(
                        1, 65L, BigInteger.ZERO, BigInteger.ZERO, 0L))
                .isInstanceOf(IllegalArgumentException.class);

        // lengthInBits must fit in the remaining destination array.
        assertThatThrownBy(() ->
                        ImmutableBigIntegerArrayUtil.validateGetParameters(1, 0L, BigInteger.ZERO, SIXTY_FIVE, 65L))
                .isInstanceOf(IllegalArgumentException.class);

        // ibaStartInBits must not exceed ibaLengthInBits.
        assertThatThrownBy(() -> ImmutableBigIntegerArrayUtil.validateGetParameters(1, 0L, SIXTY_FIVE, SIXTY_FOUR, 0L))
                .isInstanceOf(IllegalArgumentException.class);

        // lengthInBits must fit in the remaining source range.
        // The destination has enough room, so this specifically exercises the source check.
        assertThatThrownBy(() -> ImmutableBigIntegerArrayUtil.validateGetParameters(2, 0L, SIXTY_THREE, SIXTY_FOUR, 2L))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void maximumBitArrayOffsetTest() {
        final int maximumArrayLength = Integer.MAX_VALUE - 8;

        assertThatCode(() -> ImmutableBigIntegerArrayUtil.validateGetParameters(
                        maximumArrayLength, MAX_BITS_IN_ARRAY, BigInteger.ZERO, BigInteger.ZERO, 0L))
                .doesNotThrowAnyException();

        assertThatCode(() -> ImmutableBigIntegerArrayUtil.validateGetParameters(
                        maximumArrayLength,
                        0L,
                        BigInteger.ZERO,
                        BigInteger.valueOf(MAX_BITS_IN_ARRAY - 1),
                        MAX_BITS_IN_ARRAY - 1))
                .doesNotThrowAnyException();
    }
}
