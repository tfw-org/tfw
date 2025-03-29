package tfw.immutable.iba;

import static tfw.immutable.iba.bitiba.BitIba.MAX_BITS_IN_ARRAY;

import java.math.BigInteger;
import tfw.check.Argument;

public class ImmutableBigIntegerArrayUtil {
    private ImmutableBigIntegerArrayUtil() {}

    public static void validateGetParameters(
            int arrayLength, int arrayOffset, BigInteger ibaStart, BigInteger ibaLength, int length) {
        Argument.assertNotLessThan(arrayOffset, 0, "offset");
        Argument.assertLessThan(arrayOffset, MAX_BITS_IN_ARRAY, "arrayOffset", "MAX_BITS_IN_ARRAY");
        Argument.assertLessThan(arrayOffset + (long) length, arrayLength * (long) Long.SIZE, "offset", "array.length");
        Argument.assertGreaterThanOrEqualTo(ibaStart, BigInteger.ZERO, "ibaStart");
        Argument.assertNotLessThan(length, 0, "length");
        Argument.assertLessThan(length, MAX_BITS_IN_ARRAY, "length", "MAX_BITS_IN_ARRAY");
        Argument.assertNotGreaterThan(
                ibaStart.add(BigInteger.valueOf(length)), ibaLength, "start+length", "Iba.length()");
    }

    public static void validateGetParameters(
            int arrayLength,
            long arrayOffsetInBits,
            BigInteger ibaStartInBits,
            BigInteger ibaLengthInBits,
            long lengthInBits) {
        Argument.assertNotLessThan(arrayOffsetInBits, 0, "offset");
        Argument.assertLessThan(arrayOffsetInBits, MAX_BITS_IN_ARRAY, "arrayOffsetInBits", "MAX_BITS_IN_ARRAY");
        Argument.assertLessThan(
                arrayOffsetInBits + lengthInBits, arrayLength * (long) Long.SIZE, "offset", "array.length");
        Argument.assertGreaterThanOrEqualTo(ibaStartInBits, BigInteger.ZERO, "start");
        Argument.assertNotLessThan(lengthInBits, 0, "length");
        Argument.assertLessThan(lengthInBits, MAX_BITS_IN_ARRAY, "lengthInBits", "MAX_BITS_IN_ARRAY");
        Argument.assertNotGreaterThan(
                ibaStartInBits.add(BigInteger.valueOf(lengthInBits)),
                ibaLengthInBits,
                "start+length",
                "Iba.lengthInBits");
    }
}
