package tfw.immutable.iba;

import static tfw.immutable.iba.bitiba.BitIba.MAX_BITS_IN_ARRAY;

import java.math.BigInteger;
import tfw.check.Arguments;

public class ImmutableBigIntegerArrayUtil {
    private static final String MAX_BITS_IN_ARRAY_STRING = "MAX_BITS_IN_ARRAY";

    private ImmutableBigIntegerArrayUtil() {}

    public static void validateGetParameters(
            int arrayLength, int arrayOffset, BigInteger ibaStart, BigInteger ibaLength, int length) {
        Arguments.checkGreaterThanOrEqual(arrayOffset, "arrayOffset", 0);
        Arguments.checkLessThan(arrayOffset, "arrayOffset", MAX_BITS_IN_ARRAY, MAX_BITS_IN_ARRAY_STRING);
        Arguments.checkGreaterThanOrEqual(length, "length", 0);
        Arguments.checkLessThan(length, "length", MAX_BITS_IN_ARRAY, MAX_BITS_IN_ARRAY_STRING);
        Arguments.checkGreaterThanOrEqual(ibaLength, "ibaLength", BigInteger.ZERO);
        Arguments.checkGreaterThanOrEqual(ibaStart, "ibaStart", BigInteger.ZERO);
        Arguments.checkLessThanOrEqual(arrayOffset, "arrayOffset", arrayLength, "arrayLength");
        Arguments.checkLessThanOrEqual(length, "length", arrayLength - arrayOffset, "arrayLength-arrayOffset");
        Arguments.checkLessThanOrEqual(ibaStart, "ibaStart", ibaLength, "ibaLength");
        Arguments.checkLessThanOrEqual(
                BigInteger.valueOf(length), "length", ibaLength.subtract(ibaStart), "ibaLength-ibaStart");
    }

    public static void validateGetParameters(
            int arrayLength,
            long arrayOffsetInBits,
            BigInteger ibaStartInBits,
            BigInteger ibaLengthInBits,
            long lengthInBits) {
        final long arrayLengthInBits = arrayLength * (long) Long.SIZE;

        Arguments.checkGreaterThanOrEqual(arrayOffsetInBits, "arrayOffsetInBits", 0);
        Arguments.checkLessThan(arrayOffsetInBits, "arrayOffsetInBits", MAX_BITS_IN_ARRAY, MAX_BITS_IN_ARRAY_STRING);
        Arguments.checkGreaterThanOrEqual(lengthInBits, "lengthInBits", 0);
        Arguments.checkLessThan(lengthInBits, "lengthInBits", MAX_BITS_IN_ARRAY, MAX_BITS_IN_ARRAY_STRING);
        Arguments.checkGreaterThanOrEqual(ibaLengthInBits, "ibaLengthInBits", BigInteger.ZERO);
        Arguments.checkGreaterThanOrEqual(ibaStartInBits, "ibaStartInBits", BigInteger.ZERO);
        Arguments.checkLessThanOrEqual(
                arrayOffsetInBits, "arrayOffsetInBits", arrayLengthInBits, "arrayLength*Long.SIZE");
        Arguments.checkLessThanOrEqual(
                lengthInBits,
                "lengthInBits",
                arrayLengthInBits - arrayOffsetInBits,
                "arrayLength*Long.SIZE-arrayOffsetInBits");
        Arguments.checkLessThanOrEqual(ibaStartInBits, "ibaStartInBits", ibaLengthInBits, "ibaLengthInBits");
        Arguments.checkLessThanOrEqual(
                BigInteger.valueOf(lengthInBits),
                "lengthInBits",
                ibaLengthInBits.subtract(ibaStartInBits),
                "ibaLengthInBits-ibaStartInBits");
    }
}
