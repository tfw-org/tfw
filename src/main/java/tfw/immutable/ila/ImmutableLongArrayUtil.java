package tfw.immutable.ila;

import tfw.check.Argument;

public final class ImmutableLongArrayUtil {
    private static final String ILA_LENGTH_LABEL = "ila.length()";
    private static final String ARRAY_LENGTH_LABEL = "array.length";
    private static final String OFFSET_LABEL = "offset";
    private static final String START_LABEL = "start";
    private static final String LENGTH_LABEL = "length";
    private static final String OFFSET_PLUS_LENGTH_LABEL = OFFSET_LABEL + "+" + LENGTH_LABEL;
    private static final String START_PLUS_LENGTH_LABEL = START_LABEL + "+" + LENGTH_LABEL;

    private ImmutableLongArrayUtil() {}

    public static void boundsCheck(long ilaLength, int arrayLength, int offset, long start, int length) {
        Argument.assertNotLessThan(ilaLength, 0, ILA_LENGTH_LABEL);
        Argument.assertNotLessThan(arrayLength, 0, ARRAY_LENGTH_LABEL);
        Argument.assertNotLessThan(offset, 0, OFFSET_LABEL);
        Argument.assertNotLessThan(start, 0, START_LABEL);
        Argument.assertNotLessThan(length, 0, LENGTH_LABEL);
        Argument.assertNotGreaterThanOrEquals(offset, arrayLength, OFFSET_LABEL, ARRAY_LENGTH_LABEL);
        Argument.assertNotGreaterThanOrEquals(start, ilaLength, START_LABEL, ILA_LENGTH_LABEL);
        Argument.assertNotGreaterThan(
                (long) offset + length, arrayLength, OFFSET_PLUS_LENGTH_LABEL, ARRAY_LENGTH_LABEL);
        Argument.assertNotGreaterThan(start + length, ilaLength, START_PLUS_LENGTH_LABEL, ILA_LENGTH_LABEL);
    }
}
