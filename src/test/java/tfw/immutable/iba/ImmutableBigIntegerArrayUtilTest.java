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
    void validateElementParametersAcceptsValidRange() {
        assertThatCode(() -> ImmutableBigIntegerArrayUtil.validateGetParameters(3, 1, TWO, FOUR, 2))
                .doesNotThrowAnyException();
    }

    @Test
    void validateElementParametersRejectsNegativeArrayLength() {
        assertThatThrownBy(() -> ImmutableBigIntegerArrayUtil.validateGetParameters(
                        Integer.MIN_VALUE, 1, BigInteger.ZERO, THREE, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("arrayLength (=" + Integer.MIN_VALUE + ") < 0 not allowed!");
    }

    @Test
    void validateElementParametersRejectsNegativeArrayOffset() {
        assertThatThrownBy(() -> ImmutableBigIntegerArrayUtil.validateGetParameters(3, -1, BigInteger.ZERO, THREE, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("arrayOffset (=-1) < 0 not allowed!");
    }

    @Test
    void validateElementParametersRejectsNegativeLength() {
        assertThatThrownBy(() -> ImmutableBigIntegerArrayUtil.validateGetParameters(3, 0, BigInteger.ZERO, THREE, -1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("length (=-1) < 0 not allowed!");
    }

    @Test
    void validateElementParametersRejectsNullIbaLength() {
        assertThatThrownBy(() -> ImmutableBigIntegerArrayUtil.validateGetParameters(3, 0, BigInteger.ZERO, null, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ibaLength (=null) == null not allowed!");
    }

    @Test
    void validateElementParametersRejectsNullIbaStart() {
        assertThatThrownBy(() -> ImmutableBigIntegerArrayUtil.validateGetParameters(3, 0, null, THREE, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ibaStart == null not allowed!");
    }

    @Test
    void validateElementParametersRejectsNegativeIbaStart() {
        assertThatThrownBy(() -> ImmutableBigIntegerArrayUtil.validateGetParameters(3, 0, NEGATIVE_ONE, THREE, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ibaStart (=-1) < 0 not allowed!");
    }

    @Test
    void validateElementParametersRejectsArrayRangeBeyondEnd() {
        assertThatThrownBy(() -> ImmutableBigIntegerArrayUtil.validateGetParameters(3, 1, BigInteger.ZERO, FOUR, 3))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("length (=3) > arrayLength-arrayOffset (=2) not allowed!");
    }

    @Test
    void validateElementParametersRejectsSourceRangeBeyondEnd() {
        assertThatThrownBy(() -> ImmutableBigIntegerArrayUtil.validateGetParameters(5, 0, TWO, THREE, 2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("length (=2) > ibaLength-ibaStart (=1) not allowed!");
    }

    @Test
    void validateElementParametersRejectsNegativeIbaLength() {
        assertThatThrownBy(() ->
                        ImmutableBigIntegerArrayUtil.validateGetParameters(3, 0, BigInteger.ZERO, NEGATIVE_ONE, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("length (=0) > ibaLength-ibaStart (=-1) not allowed!");
    }

    @Test
    void validateElementParametersRejectsIbaStartBeyondIbaLength() {
        assertThatThrownBy(() -> ImmutableBigIntegerArrayUtil.validateGetParameters(3, 0, FOUR, THREE, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("length (=0) > ibaLength-ibaStart (=-1) not allowed!");
    }

    @Test
    void validateBitParametersAcceptsValidRange() {
        assertThatCode(() -> ImmutableBigIntegerArrayUtil.validateGetParameters(2, 1L, SIXTY_THREE, SIXTY_FIVE, 2L))
                .doesNotThrowAnyException();
    }

    @Test
    void validateBitParametersRejectsNegativeArrayLength() {
        assertThatThrownBy(() ->
                        ImmutableBigIntegerArrayUtil.validateGetParameters(-1, 0L, BigInteger.ZERO, SIXTY_FOUR, 0L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("lengthInBits (=0) > arrayLength*Long.SIZE-arrayOffsetInBits (=-64) not allowed!");
    }

    @Test
    void validateBitParametersRejectsNegativeArrayOffset() {
        assertThatThrownBy(() ->
                        ImmutableBigIntegerArrayUtil.validateGetParameters(1, -1L, BigInteger.ZERO, SIXTY_FOUR, 0L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("arrayOffsetInBits (=-1) < 0 not allowed!");
    }

    @Test
    void validateBitParametersRejectsNegativeLength() {
        assertThatThrownBy(() ->
                        ImmutableBigIntegerArrayUtil.validateGetParameters(1, 0L, BigInteger.ZERO, SIXTY_FOUR, -1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("lengthInBits (=-1) < 0 not allowed!");
    }

    @Test
    void validateBitParametersRejectsNullIbaLength() {
        assertThatThrownBy(() -> ImmutableBigIntegerArrayUtil.validateGetParameters(1, 0L, BigInteger.ZERO, null, 0L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ibaLengthInBits (=null) == null not allowed!");
    }

    @Test
    void validateBitParametersRejectsNullIbaStart() {
        assertThatThrownBy(() -> ImmutableBigIntegerArrayUtil.validateGetParameters(1, 0L, null, SIXTY_FOUR, 0L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ibaStartInBits == null not allowed!");
    }

    @Test
    void validateBitParametersRejectsNegativeIbaStart() {
        assertThatThrownBy(
                        () -> ImmutableBigIntegerArrayUtil.validateGetParameters(1, 0L, NEGATIVE_ONE, SIXTY_FOUR, 0L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ibaStartInBits (=-1) < 0 not allowed!");
    }

    @Test
    void validateBitParametersRejectsArrayOffsetBeyondMaximum() {
        assertThatThrownBy(() -> ImmutableBigIntegerArrayUtil.validateGetParameters(
                        Integer.MAX_VALUE, MAX_BITS_IN_ARRAY + 1, BigInteger.ZERO, MAX_BITS_IN_ARRAY_BIG_INTEGER, 0L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("arrayOffsetInBits (=" + (MAX_BITS_IN_ARRAY + 1) + ") > " + MAX_BITS_IN_ARRAY
                        + " not allowed!");
    }

    @Test
    void validateBitParametersRejectsLengthAtMaximum() {
        assertThatThrownBy(() -> ImmutableBigIntegerArrayUtil.validateGetParameters(
                        Integer.MAX_VALUE, 0L, BigInteger.ZERO, MAX_BITS_IN_ARRAY_BIG_INTEGER, MAX_BITS_IN_ARRAY))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("lengthInBits (=" + MAX_BITS_IN_ARRAY + ") >= " + MAX_BITS_IN_ARRAY + " not allowed!");
    }

    @Test
    void validateBitParametersRejectsArrayOffsetBeyondEnd() {
        assertThatThrownBy(() ->
                        ImmutableBigIntegerArrayUtil.validateGetParameters(1, 65L, BigInteger.ZERO, SIXTY_FOUR, 0L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("lengthInBits (=0) > arrayLength*Long.SIZE-arrayOffsetInBits (=-1) not allowed!");
    }

    @Test
    void validateBitParametersRejectsArrayRangeBeyondEndWithNonzeroOffset() {
        assertThatThrownBy(() ->
                        ImmutableBigIntegerArrayUtil.validateGetParameters(1, 1L, BigInteger.ZERO, SIXTY_FIVE, 64L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("lengthInBits (=64) > arrayLength*Long.SIZE-arrayOffsetInBits (=63) not allowed!");
    }

    @Test
    void validateBitParametersRejectsSourceRangeBeyondEnd() {
        assertThatThrownBy(() -> ImmutableBigIntegerArrayUtil.validateGetParameters(2, 0L, SIXTY_THREE, SIXTY_FOUR, 2L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("lengthInBits (=2) > ibaLengthInBits-ibaStartInBits (=1) not allowed!");
    }

    @Test
    void validateBitParametersRejectsNegativeIbaLength() {
        assertThatThrownBy(() ->
                        ImmutableBigIntegerArrayUtil.validateGetParameters(1, 0L, BigInteger.ZERO, NEGATIVE_ONE, 0L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("lengthInBits (=0) > ibaLengthInBits-ibaStartInBits (=-1) not allowed!");
    }

    @Test
    void validateBitParametersRejectsIbaStartBeyondIbaLength() {
        assertThatThrownBy(() -> ImmutableBigIntegerArrayUtil.validateGetParameters(1, 0L, SIXTY_FIVE, SIXTY_FOUR, 0L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("lengthInBits (=0) > ibaLengthInBits-ibaStartInBits (=-1) not allowed!");
    }

    @Test
    void validateBitParametersAcceptsMaximumValidLength() {
        assertThatCode(() -> ImmutableBigIntegerArrayUtil.validateGetParameters(
                        Integer.MAX_VALUE, 0L, BigInteger.ZERO, MAX_BITS_IN_ARRAY_BIG_INTEGER, MAX_BITS_IN_ARRAY - 1))
                .doesNotThrowAnyException();
    }

    @Test
    void validateBitParametersAcceptsMaximumValidOffset() {
        assertThatCode(() -> ImmutableBigIntegerArrayUtil.validateGetParameters(
                        Integer.MAX_VALUE, MAX_BITS_IN_ARRAY, BigInteger.ZERO, MAX_BITS_IN_ARRAY_BIG_INTEGER, 0L))
                .doesNotThrowAnyException();
    }
}
