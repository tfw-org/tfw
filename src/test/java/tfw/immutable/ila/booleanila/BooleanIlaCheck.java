package tfw.immutable.ila.booleanila;

import java.util.Random;

/**
 *
 * @immutables.types=nonnumeric
 */
public final class BooleanIlaCheck {
    private BooleanIlaCheck() {
        // non-instantiable class
    }

    public static void checkAll(
            BooleanIla target, BooleanIla actual, int addlOffsetLength, int maxAbsStride, boolean epsilon)
            throws Exception {
        BooleanIlaUtilCheck.checkAll(actual, epsilon);
        checkFourFiveEquivalence(actual, addlOffsetLength, epsilon);
        checkCorrectness(target, actual, addlOffsetLength, maxAbsStride, epsilon);
    }

    public static void checkWithoutCorrectness(BooleanIla actual, int offsetLength, boolean epsilon) throws Exception {
        BooleanIlaUtilCheck.checkAll(actual, epsilon);
        checkFourFiveEquivalence(actual, offsetLength, epsilon);
    }

    public static void checkFourFiveEquivalence(BooleanIla ila, int offsetLength, boolean epsilon) throws Exception {
        if (epsilon != false) {
            throw new IllegalArgumentException("epsilon != " + (false) + " not allowed");
        } else {
            if (offsetLength < 0) throw new Exception("offsetLength < 0 not allowed");
            final Random random = new Random(0);
            final int ilaLength = ila.length() + offsetLength <= Integer.MAX_VALUE
                    ? (int) ila.length()
                    : Integer.MAX_VALUE - offsetLength;
            for (int offset = 0; offset < offsetLength; ++offset) {
                final boolean[] four = new boolean[ilaLength + offsetLength];
                final boolean[] five = new boolean[ilaLength + offsetLength];
                for (int length = 1; length <= ilaLength; ++length) {
                    for (long start = 0; start < ilaLength - length + 1; ++start) {
                        for (int ii = 0; ii < four.length; ++ii) {
                            five[ii] = four[ii] = random.nextBoolean();
                        }
                        ila.toArray(four, offset, start, length);
                        ila.toArray(five, offset, 1, start, length);
                        for (int ii = 0; ii < length; ++ii) {
                            if (!(four[ii] == five[ii]))
                                throw new Exception("four[" + ii + "] ("
                                        + four[ii] + ") !~ five["
                                        + ii + "] ("
                                        + five[ii]
                                        + ") {length=" + length
                                        + ",start=" + start
                                        + ",offset=" + offset
                                        + "}");
                        }
                    }
                }
            }
        }
    }

    public static void checkCorrectness(
            BooleanIla target, BooleanIla actual, int addlOffsetLength, int maxAbsStride, boolean epsilon)
            throws Exception {
        if (epsilon != false) {
            throw new IllegalArgumentException("epsilon != " + (false) + " not allowed");
        } else {
            if (addlOffsetLength < 0) throw new Exception("addlOffsetLength < 0 not allowed");
            if (maxAbsStride < 1) throw new Exception("maxAbsStride < 1 not allowed");
            if (target.length() != actual.length()) throw new Exception("target.length() != actual.length()");
            final Random random = new Random(0);
            final int ilaLength = target.length() + addlOffsetLength <= Integer.MAX_VALUE
                    ? (int) target.length()
                    : Integer.MAX_VALUE - addlOffsetLength;
            for (int stride = -maxAbsStride; stride <= maxAbsStride; ++stride) {
                if (stride != 0) {
                    int absStride = stride < 0 ? -stride : stride;
                    int offsetStart = stride < 0 ? (ilaLength - 1) * absStride : 0;
                    int offsetEnd = offsetStart + addlOffsetLength;
                    for (int offset = offsetStart; offset < offsetEnd; ++offset) {
                        final int arraySize = (ilaLength - 1) * absStride + 1 + addlOffsetLength;
                        final boolean[] targetBase = new boolean[arraySize];
                        final boolean[] actualBase = new boolean[arraySize];
                        for (int length = 1; length <= ilaLength; ++length) {
                            for (long start = 0; start < ilaLength - length + 1; ++start) {
                                for (int ii = 0; ii < targetBase.length; ++ii) {
                                    targetBase[ii] = actualBase[ii] = random.nextBoolean();
                                }
                                target.toArray(targetBase, offset, stride, start, length);
                                actual.toArray(actualBase, offset, stride, start, length);
                                for (int ii = 0; ii < arraySize; ++ii) {
                                    if (!(actualBase[ii] == targetBase[ii]))
                                        throw new Exception("actual[" + ii
                                                + "] ("
                                                + actualBase[ii]
                                                + ") !~ target["
                                                + ii + "] ("
                                                + targetBase[ii]
                                                + ") {length="
                                                + length
                                                + ",start=" + start
                                                + ",offset="
                                                + offset
                                                + ",stride="
                                                + stride
                                                + "}");
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static void dump(String msg, boolean[] array) {
        System.out.println(msg + ":");
        for (int ii = 0; ii < array.length; ++ii) {
            System.out.print(" " + array[ii]);
        }
        System.out.println();
    }
}
// AUTO GENERATED FROM TEMPLATE
