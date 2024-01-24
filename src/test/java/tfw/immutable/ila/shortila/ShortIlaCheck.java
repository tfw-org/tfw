package tfw.immutable.ila.shortila;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.Random;

public final class ShortIlaCheck {
    private ShortIlaCheck() {
        // non-instantiable class
    }

    public static void checkGetArguments(final ShortIla ila) throws IOException {
        final long ilaLength = ila.length();
        final short[] array = new short[10];

        assertThrows(NullPointerException.class, () -> ila.get(null, 0, 0, 1));
        assertThrows(IllegalArgumentException.class, () -> ila.get(array, -1, 0, 1));
        assertThrows(IllegalArgumentException.class, () -> ila.get(array, 0, -1, 1));
        assertThrows(IllegalArgumentException.class, () -> ila.get(array, 0, 0, -1));
        assertThrows(IllegalArgumentException.class, () -> ila.get(array, array.length, 0, 1));
        assertThrows(IllegalArgumentException.class, () -> ila.get(array, 0, ilaLength, 1));
        assertThrows(IllegalArgumentException.class, () -> ila.get(array, array.length - 1, 0, 2));
        assertThrows(IllegalArgumentException.class, () -> ila.get(array, 0, ilaLength - 1, 2));
    }

    public static void checkGetExhaustively(final ShortIla ila1, final ShortIla ila2) throws IOException {
        final int length1 = (int) Math.min(ila1.length(), Integer.MAX_VALUE);
        final int length2 = (int) Math.min(ila2.length(), Integer.MAX_VALUE);

        assertEquals(length1, length2);

        final short[] a1 = new short[length1];
        final short[] a2 = new short[length1];

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

    public static void check(final ShortIla expectedIla, final ShortIla actualIla) throws IOException {
        checkGetArguments(actualIla);
        checkGetExhaustively(expectedIla, actualIla);
    }

    public static void checkAll(
            final ShortIla target, final ShortIla actual, int addlOffsetLength, int maxAbsStride, short epsilon)
            throws Exception {
        checkGetArguments(actual);
        ShortIlaUtilCheck.checkAll(actual, epsilon);
        checkFourFiveEquivalence(actual, addlOffsetLength, epsilon);
        checkCorrectness(target, actual, addlOffsetLength, maxAbsStride, epsilon);
    }

    public static void checkWithoutCorrectness(ShortIla ila, int offsetLength, short epsilon) throws Exception {
        checkGetArguments(ila);
        ShortIlaUtilCheck.checkAll(ila, epsilon);
        checkFourFiveEquivalence(ila, offsetLength, epsilon);
    }

    public static void checkFourFiveEquivalence(ShortIla ila, int offsetLength, short epsilon) throws Exception {
        if (offsetLength < 0) throw new Exception("offsetLength < 0 not allowed");

        final StridedShortIla stridedShortIla = StridedShortIlaFromShortIla.create(ila, new short[1000]);
        final short eps = epsilon < 0.0 ? (short) (-epsilon) : (short) (epsilon);
        final short neps = (short) (-eps);
        final Random random = new Random(0);
        final int ilaLength = ila.length() + offsetLength <= Integer.MAX_VALUE
                ? (int) ila.length()
                : Integer.MAX_VALUE - offsetLength;
        for (int offset = 0; offset < offsetLength; ++offset) {
            final short[] four = new short[ilaLength + offsetLength];
            final short[] five = new short[ilaLength + offsetLength];
            for (int length = 1; length <= ilaLength; ++length) {
                for (long start = 0; start < ilaLength - length + 1; ++start) {
                    for (int ii = 0; ii < four.length; ++ii) {
                        five[ii] = four[ii] = (short) random.nextInt();
                    }
                    ila.get(four, offset, start, length);
                    stridedShortIla.get(five, offset, 1, start, length);
                    for (int ii = 0; ii < length; ++ii) {
                        short delta = (short) (four[ii] - five[ii]);
                        if (!(neps <= delta && delta <= eps))
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

    public static void checkCorrectness(
            final ShortIla target, final ShortIla actual, int addlOffsetLength, int maxAbsStride, short epsilon)
            throws Exception {
        if (addlOffsetLength < 0) throw new Exception("addlOffsetLength < 0 not allowed");
        if (maxAbsStride < 1) throw new Exception("maxAbsStride < 1 not allowed");
        if (target.length() != actual.length()) throw new Exception("target.length() != actual.length()");

        final StridedShortIla stridedTarget = StridedShortIlaFromShortIla.create(target, new short[1000]);
        final StridedShortIla stridedActual = StridedShortIlaFromShortIla.create(target, new short[1000]);
        final short eps = epsilon < 0.0 ? (short) (-epsilon) : (short) (epsilon);
        final short neps = (short) (-eps);
        final Random random = new Random(0);
        final int ilaLength = stridedTarget.length() + addlOffsetLength <= Integer.MAX_VALUE
                ? (int) stridedTarget.length()
                : Integer.MAX_VALUE - addlOffsetLength;
        for (int stride = -maxAbsStride; stride <= maxAbsStride; ++stride) {
            if (stride != 0) {
                int absStride = stride < 0 ? -stride : stride;
                int offsetStart = stride < 0 ? (ilaLength - 1) * absStride : 0;
                int offsetEnd = offsetStart + addlOffsetLength;
                for (int offset = offsetStart; offset < offsetEnd; ++offset) {
                    final int arraySize = (ilaLength - 1) * absStride + 1 + addlOffsetLength;
                    final short[] targetBase = new short[arraySize];
                    final short[] actualBase = new short[arraySize];
                    for (int length = 1; length <= ilaLength; ++length) {
                        for (long start = 0; start < ilaLength - length + 1; ++start) {
                            for (int ii = 0; ii < targetBase.length; ++ii) {
                                targetBase[ii] = actualBase[ii] = (short) random.nextInt();
                            }
                            stridedTarget.get(targetBase, offset, stride, start, length);
                            stridedActual.get(actualBase, offset, stride, start, length);
                            for (int ii = 0; ii < arraySize; ++ii) {
                                short delta = (short) (actualBase[ii] - targetBase[ii]);
                                if (!(neps <= delta && delta <= eps))
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

    public static void dump(String msg, short[] array) {
        System.out.println(msg + ":");
        for (int ii = 0; ii < array.length; ++ii) {
            System.out.print(" " + array[ii]);
        }
        System.out.println();
    }
}
// AUTO GENERATED FROM TEMPLATE
