package tfw.immutable.ila.objectila;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

public final class ObjectIlaCheck {
    private ObjectIlaCheck() {
        // non-instantiable class
    }

    public static void checkGetArguments(final ObjectIla<Object> ila) throws IOException {
        final long ilaLength = ila.length();
        final Object[] array = new Object[10];

        assertThrows(NullPointerException.class, () -> ila.get(null, 0, 0, 1));
        assertThrows(IllegalArgumentException.class, () -> ila.get(array, -1, 0, 1));
        assertThrows(IllegalArgumentException.class, () -> ila.get(array, 0, -1, 1));
        assertThrows(IllegalArgumentException.class, () -> ila.get(array, 0, 0, -1));
        assertThrows(IllegalArgumentException.class, () -> ila.get(array, array.length, 0, 1));
        assertThrows(IllegalArgumentException.class, () -> ila.get(array, 0, ilaLength, 1));
        assertThrows(IllegalArgumentException.class, () -> ila.get(array, array.length - 1, 0, 2));
        assertThrows(IllegalArgumentException.class, () -> ila.get(array, 0, ilaLength - 1, 2));
    }

    public static void checkGetExhaustively(ObjectIla<Object> ila1, ObjectIla<Object> ila2) throws IOException {
        final int length1 = (int) Math.min(ila1.length(), Integer.MAX_VALUE);
        final int length2 = (int) Math.min(ila2.length(), Integer.MAX_VALUE);

        assertEquals(length1, length2);

        final Object[] a1 = new Object[length1];
        final Object[] a2 = new Object[length1];

        for (int s = 0; s < length1; s++) {
            for (int l = 0; l < length1 - s; l++) {
                for (int o = 0; o < length1 - l; o++) {
                    ila1.get(a1, o, s, l);
                    ila2.get(a2, o, s, l);

                    assertArrayEquals(a1, a2);
                }
            }
        }
    }

    public static void check(final ObjectIla expectedIla, final ObjectIla actualIla) throws IOException {
        checkGetArguments(actualIla);
        checkGetExhaustively(expectedIla, actualIla);
    }

    public static void checkAll(
            ObjectIla<Object> target, ObjectIla<Object> actual, int addlOffsetLength, int maxAbsStride, Object epsilon)
            throws Exception {
        checkGetArguments(actual);
        checkFourFiveEquivalence(actual, addlOffsetLength, epsilon);
        checkCorrectness(target, actual, addlOffsetLength, maxAbsStride, epsilon);
    }

    public static void checkWithoutCorrectness(final ObjectIla<Object> actual, final int offsetLength, Object epsilon)
            throws Exception {
        checkGetArguments(actual);
        checkFourFiveEquivalence(actual, offsetLength, epsilon);
    }

    public static void checkFourFiveEquivalence(final ObjectIla<Object> ila, final int offsetLength, Object epsilon)
            throws Exception {
        if (epsilon != Object.class) {
            throw new IllegalArgumentException("epsilon != " + (Object.class) + " not allowed");
        } else {
            if (offsetLength < 0) throw new Exception("offsetLength < 0 not allowed");

            final StridedObjectIla<Object> stridedObjectIla = StridedObjectIlaFromObjectIla.create(ila, new Object[10]);
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
                        ila.get(four, offset, start, length);
                        stridedObjectIla.get(five, offset, 1, start, length);
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

            final StridedObjectIla<Object> stridedTarget = StridedObjectIlaFromObjectIla.create(target, new Object[10]);
            final StridedObjectIla<Object> stridedActual = StridedObjectIlaFromObjectIla.create(target, new Object[10]);
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
                                stridedTarget.get(targetBase, offset, stride, start, length);
                                stridedActual.get(actualBase, offset, stride, start, length);
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
