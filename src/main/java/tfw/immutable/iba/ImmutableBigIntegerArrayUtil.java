package tfw.immutable.iba;

import static tfw.immutable.iba.bitiba.BitIba.MAX_BITS_IN_ARRAY;

import java.math.BigInteger;
import tfw.check.Arguments;

public class ImmutableBigIntegerArrayUtil {
    private static final String ARRAY_LENGTH = "arrayLength";
    private static final String ARRAY_OFFSET = "arrayOffset";
    private static final String LENGTH = "length";
    private static final String ARRAY_OFFSET_IN_BITS = "arrayOffsetInBits";
    private static final String LENGTH_IN_BITS = "lengthInBits";

    private ImmutableBigIntegerArrayUtil() {}

    public static void validateGetParameters(
            int arrayLength, int arrayOffset, BigInteger ibaStart, BigInteger ibaLength, int length) {
        Arguments.checkGreaterThanOrEqual(arrayLength, ARRAY_LENGTH, 0);
        Arguments.checkGreaterThanOrEqual(arrayOffset, ARRAY_OFFSET, 0);
        Arguments.checkGreaterThanOrEqual(length, LENGTH, 0);
        Arguments.checkNotNull(ibaLength, "ibaLength");
        Arguments.checkGreaterThanOrEqual(ibaStart, "ibaStart", BigInteger.ZERO);
        Arguments.checkLessThanOrEqual(length, LENGTH, arrayLength - arrayOffset, "arrayLength-arrayOffset");
        Arguments.checkLessThanOrEqual(
                BigInteger.valueOf(length), LENGTH, ibaLength.subtract(ibaStart), "ibaLength-ibaStart");
    }

    public static void validateGetParameters(
            int arrayLength,
            long arrayOffsetInBits,
            BigInteger ibaStartInBits,
            BigInteger ibaLengthInBits,
            long lengthInBits) {
        final long arrayLengthInBits = arrayLength * (long) Long.SIZE;

        Arguments.checkGreaterThanOrEqual(arrayOffsetInBits, ARRAY_OFFSET_IN_BITS, 0);
        Arguments.checkLessThanOrEqual(arrayOffsetInBits, ARRAY_OFFSET_IN_BITS, MAX_BITS_IN_ARRAY);
        Arguments.checkGreaterThanOrEqual(lengthInBits, LENGTH_IN_BITS, 0);
        Arguments.checkLessThan(lengthInBits, LENGTH_IN_BITS, MAX_BITS_IN_ARRAY);
        Arguments.checkNotNull(ibaLengthInBits, "ibaLengthInBits");
        Arguments.checkGreaterThanOrEqual(ibaStartInBits, "ibaStartInBits", BigInteger.ZERO);
        Arguments.checkLessThanOrEqual(
                lengthInBits,
                LENGTH_IN_BITS,
                arrayLengthInBits - arrayOffsetInBits,
                "arrayLength*Long.SIZE-arrayOffsetInBits");
        Arguments.checkLessThanOrEqual(
                BigInteger.valueOf(lengthInBits),
                LENGTH_IN_BITS,
                ibaLengthInBits.subtract(ibaStartInBits),
                "ibaLengthInBits-ibaStartInBits");
    }
}
