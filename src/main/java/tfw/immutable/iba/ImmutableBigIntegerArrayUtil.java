package tfw.immutable.iba;

import static tfw.immutable.iba.bitiba.BitIba.MAX_BITS_IN_ARRAY;

import java.math.BigInteger;
import tfw.check.Argument;

public class ImmutableBigIntegerArrayUtil {
    private static final String MAX_BITS_IN_ARRAY_STRING = "MAX_BITS_IN_ARRAY";

    private ImmutableBigIntegerArrayUtil() {}

    public static void validateGetParameters(
            int arrayLength, int arrayOffset, BigInteger ibaStart, BigInteger ibaLength, int length) {
        Argument.assertNotLessThan(arrayOffset, 0, "arrayOffset");
        Argument.assertLessThan(arrayOffset, MAX_BITS_IN_ARRAY, "arrayOffset", MAX_BITS_IN_ARRAY_STRING);
        Argument.assertLessThan(arrayOffset + (long) length, arrayLength, "arrayOffset+length", "arrayLength");
        Argument.assertGreaterThanOrEqualTo(ibaStart, BigInteger.ZERO, "ibaStart");
        Argument.assertNotLessThan(length, 0, "length");
        Argument.assertLessThan(length, MAX_BITS_IN_ARRAY, "length", MAX_BITS_IN_ARRAY_STRING);
        Argument.assertNotGreaterThan(
                ibaStart.add(BigInteger.valueOf(length)), ibaLength, "ibaStart+length", "IbaLength");
    }

    public static void validateGetParameters(
            int arrayLength,
            long arrayOffsetInBits,
            BigInteger ibaStartInBits,
            BigInteger ibaLengthInBits,
            long lengthInBits) {
        Argument.assertNotLessThan(arrayOffsetInBits, 0, "arrayOffsetInBits");
        Argument.assertLessThan(arrayOffsetInBits, MAX_BITS_IN_ARRAY, "arrayOffsetInBits", MAX_BITS_IN_ARRAY_STRING);
        Argument.assertLessThan(
                arrayOffsetInBits + lengthInBits,
                arrayLength * (long) Long.SIZE,
                "arrayOffsetInBits+lengthInBits",
                "arrayLength*Long.SIZE");
        Argument.assertGreaterThanOrEqualTo(ibaStartInBits, BigInteger.ZERO, "ibaStartInBits");
        Argument.assertNotLessThan(lengthInBits, 0, "lengthInBits");
        Argument.assertLessThan(lengthInBits, MAX_BITS_IN_ARRAY, "lengthInBits", MAX_BITS_IN_ARRAY_STRING);
        Argument.assertNotGreaterThan(
                ibaStartInBits.add(BigInteger.valueOf(lengthInBits)),
                ibaLengthInBits,
                "ibaStartInBits+lengthInBits",
                "IbaLengthInBits");
    }
}
