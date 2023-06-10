package tfw.immutable.ila.objectila;

/**
 *
 * @immutables.types=nonnumeric
 */
public final class ObjectIlaCheck {
    private ObjectIlaCheck() {
        // non-instantiable class
    }

    public static void checkAll(
            ObjectIla target, ObjectIla actual, int addlOffsetLength, int maxAbsStride, Object epsilon)
            throws Exception {
        checkZeroArgImmutability(actual);
        checkTwoArgImmutability(actual, epsilon);
        checkTwoFourEquivalence(actual, epsilon);
        checkFourFiveEquivalence(actual, addlOffsetLength, epsilon);
        checkCorrectness(target, actual, addlOffsetLength, maxAbsStride, epsilon);
    }

    public static void checkWithoutCorrectness(ObjectIla ila, int offsetLength, Object epsilon) throws Exception {
        checkZeroArgImmutability(ila);
        checkTwoArgImmutability(ila, epsilon);
        checkTwoFourEquivalence(ila, epsilon);
        checkFourFiveEquivalence(ila, offsetLength, epsilon);
    }

    public static void checkZeroArgImmutability(ObjectIla ila) throws Exception {
        final long firstLength = ila.length();
        final Object[] firstArray = ila.toArray();
        final long secondLength = ila.length();
        final Object[] secondArray = ila.toArray();
        final long thirdLength = ila.length();
        final Object[] thirdArray = ila.toArray();
        final long fourthLength = ila.length();

        if (firstArray.length != firstLength) throw new Exception("firstArray.length != firstLength");
        if (secondArray.length != secondLength) throw new Exception("secondArray.length != secondLength");
        if (thirdArray.length != thirdLength) throw new Exception("thirdArray.length != thirdLength");

        if (firstLength != secondLength) throw new Exception("firstLength != secondLength");
        if (secondLength != thirdLength) throw new Exception("secondLength != thirdLength");
        if (thirdLength != fourthLength) throw new Exception("thirdLength != fourthLength");

        for (int ii = 0; ii < firstLength; ++ii) {
            secondArray[ii] = new Object();
        }

        for (int ii = 0; ii < firstLength; ++ii) {
            if (firstArray[ii] != thirdArray[ii])
                throw new Exception("firstArray[" + ii + "] ("
                        + firstArray[ii] + ") != thirdArray["
                        + ii + "] (" + thirdArray[ii] + ")");
        }
    }

    // also performs zero-two equivalence
    public static void checkTwoArgImmutability(ObjectIla ila, Object epsilon) throws Exception {
        if (epsilon != Object.class) {
            throw new IllegalArgumentException("epsilon != " + (Object.class) + " not allowed");
        } else {
            final int ilaLength = ila.length() <= Integer.MAX_VALUE ? (int) ila.length() : Integer.MAX_VALUE;
            final Object[] baseline = ila.toArray(0, ilaLength);
            if (baseline.length != ilaLength) throw new Exception("baseline.length != ilaLength");
            for (int length = 1; length <= ilaLength; ++length) {
                for (long start = 0; start < ilaLength - length + 1; ++start) {
                    final Object[] subset = ila.toArray(start, length);
                    if (subset.length != length) throw new Exception("subset.length != length");
                    for (int ii = 0; ii < subset.length; ++ii) {
                        if (!(baseline[ii + (int) start].equals(subset[ii])))
                            throw new Exception("subset[" + ii + "] ("
                                    + subset[ii] + ") !~ baseline["
                                    + (ii + start) + "] ("
                                    + baseline[ii + (int) start]
                                    + ") {length=" + length
                                    + ",start=" + start + "}");
                    }
                }
            }
        }
    }

    public static void checkTwoFourEquivalence(ObjectIla ila, Object epsilon) throws Exception {
        if (epsilon != Object.class) {
            throw new IllegalArgumentException("epsilon != " + (Object.class) + " not allowed");
        } else {
            final int ilaLength = ila.length() <= Integer.MAX_VALUE ? (int) ila.length() : Integer.MAX_VALUE;
            final Object[] four = new Object[ilaLength];
            for (int length = 1; length <= ilaLength; ++length) {
                for (long start = 0; start < ilaLength - length + 1; ++start) {
                    final Object[] two = ila.toArray(start, length);
                    ila.toArray(four, 0, start, length);
                    for (int ii = 0; ii < length; ++ii) {
                        if (!(four[ii].equals(two[ii])))
                            throw new Exception("four[" + ii + "] ("
                                    + four[ii] + ") !~ two["
                                    + ii + "] ("
                                    + two[ii]
                                    + ") {length=" + length
                                    + ",start=" + start + "}");
                    }
                }
            }
        }
    }

    public static void checkFourFiveEquivalence(ObjectIla ila, int offsetLength, Object epsilon) throws Exception {
        if (epsilon != Object.class) {
            throw new IllegalArgumentException("epsilon != " + (Object.class) + " not allowed");
        } else {
            if (offsetLength < 0) throw new Exception("offsetLength < 0 not allowed");
            final int ilaLength = ila.length() + offsetLength <= Integer.MAX_VALUE
                    ? (int) ila.length()
                    : Integer.MAX_VALUE - offsetLength;
            for (int offset = 0; offset < offsetLength; ++offset) {
                final Object[] four = new Object[ilaLength + offsetLength];
                final Object[] five = new Object[ilaLength + offsetLength];
                for (int length = 1; length <= ilaLength; ++length) {
                    for (long start = 0; start < ilaLength - length + 1; ++start) {
                        for (int ii = 0; ii < four.length; ++ii) {
                            five[ii] = four[ii] = new Object();
                        }
                        ila.toArray(four, offset, start, length);
                        ila.toArray(five, offset, 1, start, length);
                        for (int ii = 0; ii < length; ++ii) {
                            if (!(four[ii].equals(five[ii])))
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
            ObjectIla target, ObjectIla actual, int addlOffsetLength, int maxAbsStride, Object epsilon)
            throws Exception {
        if (epsilon != Object.class) {
            throw new IllegalArgumentException("epsilon != " + (Object.class) + " not allowed");
        } else {
            if (addlOffsetLength < 0) throw new Exception("addlOffsetLength < 0 not allowed");
            if (maxAbsStride < 1) throw new Exception("maxAbsStride < 1 not allowed");
            if (target.length() != actual.length()) throw new Exception("target.length() != actual.length()");
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
                        final Object[] targetBase = new Object[arraySize];
                        final Object[] actualBase = new Object[arraySize];
                        for (int length = 1; length <= ilaLength; ++length) {
                            for (long start = 0; start < ilaLength - length + 1; ++start) {
                                for (int ii = 0; ii < targetBase.length; ++ii) {
                                    targetBase[ii] = actualBase[ii] = new Object();
                                }
                                target.toArray(targetBase, offset, stride, start, length);
                                actual.toArray(actualBase, offset, stride, start, length);
                                for (int ii = 0; ii < arraySize; ++ii) {
                                    if (!(actualBase[ii].equals(targetBase[ii])))
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

    public static void dump(String msg, Object[] array) {
        System.out.println(msg + ":");
        for (int ii = 0; ii < array.length; ++ii) {
            System.out.print(" " + array[ii]);
        }
        System.out.println();
    }
}
// AUTO GENERATED FROM TEMPLATE
