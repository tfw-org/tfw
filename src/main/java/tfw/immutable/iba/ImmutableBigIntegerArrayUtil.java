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
    private static final String MAX_BITS_IN_ARRAY_STRING = "MAX_BITS_IN_ARRAY";

    private ImmutableBigIntegerArrayUtil() {}

    public static void validateGetParameters(
            int arrayLength, int arrayOffset, BigInteger ibaStart, BigInteger ibaLength, int length) {
        Arguments.checkGreaterThanOrEqual(arrayLength, ARRAY_LENGTH, 0);
        Arguments.checkGreaterThanOrEqual(arrayOffset, ARRAY_OFFSET, 0);
        Arguments.checkLessThan(arrayOffset, ARRAY_OFFSET, MAX_BITS_IN_ARRAY, MAX_BITS_IN_ARRAY_STRING);
        Arguments.checkGreaterThanOrEqual(length, LENGTH, 0);
        Arguments.checkLessThan(length, LENGTH, MAX_BITS_IN_ARRAY, MAX_BITS_IN_ARRAY_STRING);
        Arguments.checkGreaterThanOrEqual(ibaLength, "ibaLength", BigInteger.ZERO);
        Arguments.checkGreaterThanOrEqual(ibaStart, "ibaStart", BigInteger.ZERO);
        Arguments.checkLessThanOrEqual(arrayOffset, ARRAY_OFFSET, arrayLength, ARRAY_LENGTH);
        Arguments.checkLessThanOrEqual(length, LENGTH, arrayLength - arrayOffset, "arrayLength-arrayOffset");
        Arguments.checkLessThanOrEqual(ibaStart, "ibaStart", ibaLength, "ibaLength");
        Arguments.checkLessThanOrEqual(
                BigInteger.valueOf(length), LENGTH, ibaLength.subtract(ibaStart), "ibaLength-ibaStart");
    }

    public static void validateGetParameters(
            int arrayLength,
            long arrayOffsetInBits,
            BigInteger ibaStartInBits,
            BigInteger ibaLengthInBits,
            long lengthInBits) {
        Arguments.checkGreaterThanOrEqual(arrayLength, ARRAY_LENGTH, 0);

        final long arrayLengthInBits = arrayLength * (long) Long.SIZE;

        Arguments.checkGreaterThanOrEqual(arrayOffsetInBits, ARRAY_OFFSET_IN_BITS, 0);
        Arguments.checkLessThanOrEqual(
                arrayOffsetInBits, ARRAY_OFFSET_IN_BITS, MAX_BITS_IN_ARRAY, MAX_BITS_IN_ARRAY_STRING);
        Arguments.checkGreaterThanOrEqual(lengthInBits, LENGTH_IN_BITS, 0);
        Arguments.checkLessThan(lengthInBits, LENGTH_IN_BITS, MAX_BITS_IN_ARRAY, MAX_BITS_IN_ARRAY_STRING);
        Arguments.checkGreaterThanOrEqual(ibaLengthInBits, "ibaLengthInBits", BigInteger.ZERO);
        Arguments.checkGreaterThanOrEqual(ibaStartInBits, "ibaStartInBits", BigInteger.ZERO);
        Arguments.checkLessThanOrEqual(
                arrayOffsetInBits, ARRAY_OFFSET_IN_BITS, arrayLengthInBits, "arrayLength*Long.SIZE");
        Arguments.checkLessThanOrEqual(
                lengthInBits,
                LENGTH_IN_BITS,
                arrayLengthInBits - arrayOffsetInBits,
                "arrayLength*Long.SIZE-arrayOffsetInBits");
        Arguments.checkLessThanOrEqual(ibaStartInBits, "ibaStartInBits", ibaLengthInBits, "ibaLengthInBits");
        Arguments.checkLessThanOrEqual(
                BigInteger.valueOf(lengthInBits),
                LENGTH_IN_BITS,
                ibaLengthInBits.subtract(ibaStartInBits),
                "ibaLengthInBits-ibaStartInBits");
    }
}
