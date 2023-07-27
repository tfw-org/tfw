package tfw.immutable.ila.objectila;

public final class ObjectIlaCheck {
    private ObjectIlaCheck() {
        // non-instantiable class
    }

    public static void checkAll(
            ObjectIla<Object> target, ObjectIla<Object> actual, int addlOffsetLength, int maxAbsStride, Object epsilon)
            throws Exception {
        checkFourFiveEquivalence(actual, addlOffsetLength, epsilon);
        checkCorrectness(target, actual, addlOffsetLength, maxAbsStride, epsilon);
    }

    public static void checkWithoutCorrectness(final ObjectIla<Object> actual, final int offsetLength, Object epsilon)
            throws Exception {
        checkFourFiveEquivalence(actual, offsetLength, epsilon);
    }

    public static void checkFourFiveEquivalence(final ObjectIla<Object> ila, final int offsetLength, Object epsilon)
            throws Exception {
        if (epsilon != Object.class) {
            throw new IllegalArgumentException("epsilon != " + (Object.class) + " not allowed");
        } else {
            if (offsetLength < 0) throw new Exception("offsetLength < 0 not allowed");

            final StridedObjectIla<Object> stridedObjectIla = new StridedObjectIla<>(ila, new Object[1000]);
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
                        stridedObjectIla.toArray(five, offset, 1, start, length);
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
            ObjectIla<Object> target, ObjectIla<Object> actual, int addlOffsetLength, int maxAbsStride, Object epsilon)
            throws Exception {
        if (epsilon != Object.class) {
            throw new IllegalArgumentException("epsilon != " + (Object.class) + " not allowed");
        } else {
            if (addlOffsetLength < 0) throw new Exception("addlOffsetLength < 0 not allowed");
            if (maxAbsStride < 1) throw new Exception("maxAbsStride < 1 not allowed");
            if (target.length() != actual.length()) throw new Exception("target.length() != actual.length()");

            final StridedObjectIla<Object> stridedTarget = new StridedObjectIla<>(target, new Object[1000]);
            final StridedObjectIla<Object> stridedActual = new StridedObjectIla<>(target, new Object[1000]);
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
                                stridedTarget.toArray(targetBase, offset, stride, start, length);
                                stridedActual.toArray(actualBase, offset, stride, start, length);
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
