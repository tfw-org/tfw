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
        Arguments.checkLessThan(arrayOffset + (long) length, "arrayOffset+length", arrayLength, "arrayLength");
        Arguments.checkGreaterThanOrEqual(ibaStart, "ibaStart", BigInteger.ZERO);
        Arguments.checkGreaterThanOrEqual(length, "length", 0);
        Arguments.checkLessThan(length, "length", MAX_BITS_IN_ARRAY, MAX_BITS_IN_ARRAY_STRING);
        Arguments.checkLessThanOrEqual(
                ibaStart.add(BigInteger.valueOf(length)), "ibaStart+length", ibaLength, "IbaLength");
    }

    public static void validateGetParameters(
            int arrayLength,
            long arrayOffsetInBits,
            BigInteger ibaStartInBits,
            BigInteger ibaLengthInBits,
            long lengthInBits) {
        Arguments.checkGreaterThanOrEqual(arrayOffsetInBits, "arrayOffsetInBits", 0);
        Arguments.checkLessThan(arrayOffsetInBits, "arrayOffsetInBits", MAX_BITS_IN_ARRAY, MAX_BITS_IN_ARRAY_STRING);
        Arguments.checkLessThan(
                arrayOffsetInBits + lengthInBits,
                "arrayOffsetInBits+lengthInBits",
                arrayLength * (long) Long.SIZE,
                "arrayLength*Long.SIZE");
        Arguments.checkGreaterThanOrEqual(ibaStartInBits, "ibaStartInBits", BigInteger.ZERO);
        Arguments.checkGreaterThanOrEqual(lengthInBits, "lengthInBits", 0);
        Arguments.checkLessThan(lengthInBits, "lengthInBits", MAX_BITS_IN_ARRAY, MAX_BITS_IN_ARRAY_STRING);
        Arguments.checkLessThanOrEqual(
                ibaStartInBits.add(BigInteger.valueOf(lengthInBits)),
                "ibaStartInBits+lengthInBits",
                ibaLengthInBits,
                "IbaLengthInBits");
    }
}
