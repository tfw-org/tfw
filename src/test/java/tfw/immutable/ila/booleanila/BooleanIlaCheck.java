package tfw.immutable.ila.booleanila;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.util.Random;
import tfw.check.Argument;

public final class BooleanIlaCheck {
    private BooleanIlaCheck() {
        // non-instantiable class
    }

    public static void checkGetArguments(final BooleanIla ila) throws IOException {
        final long ilaLength = ila.length();
        final boolean[] array = new boolean[10];

        assertThatThrownBy(() -> ila.get(null, 0, 0, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("array == null not allowed!");
        assertThatThrownBy(() -> ila.get(array, -1, 0, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("offset (=-1) < 0 not allowed!");
        assertThatThrownBy(() -> ila.get(array, 0, -1, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("start (=-1) < 0 not allowed!");
        assertThatThrownBy(() -> ila.get(array, 0, 0, -1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("length (=-1) < 0 not allowed!");
        assertThatThrownBy(() -> ila.get(array, array.length, 0, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("offset (=10) >= array.length (=10) not allowed!");
        assertThatThrownBy(() -> ila.get(array, 0, ilaLength, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("start (=%s) >= ila.length() (=%s) not allowed!", ilaLength, ilaLength);
        assertThatThrownBy(() -> ila.get(array, array.length - 1, 0, 2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("offset+length (=11) > array.length (=10) not allowed!");
        assertThatThrownBy(() -> ila.get(array, 0, ilaLength - 1, 2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("start+length (=%s) > ila.length() (=%s) not allowed!", ilaLength + 1, ilaLength);
    }

    public static void checkGetExhaustively(BooleanIla ila1, BooleanIla ila2) throws IOException {
        final int length1 = (int) Math.min(ila1.length(), Integer.MAX_VALUE);
        final int length2 = (int) Math.min(ila2.length(), Integer.MAX_VALUE);

        assertThat(length2).isEqualTo(length1);

        final boolean[] a1 = new boolean[length1];
        final boolean[] a2 = new boolean[length1];

        for (int s = 0; s < length1; s++) {
            for (int l = 0; l < length1 - s; l++) {
                for (int o = 0; o < length1 - l; o++) {
                    ila1.get(a1, o, s, l);
                    ila2.get(a2, o, s, l);

                    assertThat(a2).isEqualTo(a1);
                }
            }
        }
    }

    public static void check(final BooleanIla expectedIla, BooleanIla actualIla) throws IOException {
        checkGetArguments(actualIla);
        checkGetExhaustively(expectedIla, actualIla);
    }

    public static void checkAll(
            BooleanIla target, BooleanIla actual, int addlOffsetLength, int maxAbsStride, boolean epsilon)
            throws Exception {
        checkGetArguments(actual);
        BooleanIlaUtilCheck.checkAll(actual, epsilon);
        checkFourFiveEquivalence(actual, addlOffsetLength, epsilon);
        checkCorrectness(target, actual, addlOffsetLength, maxAbsStride, epsilon);
    }

    public static void checkWithoutCorrectness(final BooleanIla actual, final int offsetLength, boolean epsilon)
            throws Exception {
        checkGetArguments(actual);
        BooleanIlaUtilCheck.checkAll(actual, epsilon);
        checkFourFiveEquivalence(actual, offsetLength, epsilon);
    }

    public static void checkFourFiveEquivalence(final BooleanIla ila, final int offsetLength, boolean epsilon)
            throws Exception {
        Argument.assertNotLessThan(offsetLength, 0, "offsetLength");

        final StridedBooleanIla stridedBooleanIla = StridedBooleanIlaFromBooleanIla.create(ila, new boolean[10]);
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
                    ila.get(four, offset, start, length);
                    stridedBooleanIla.get(five, offset, 1, start, length);
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

    public static void checkCorrectness(
            BooleanIla target, BooleanIla actual, int addlOffsetLength, int maxAbsStride, boolean epsilon)
            throws Exception {
        Argument.assertNotLessThan(addlOffsetLength, 0, "addlOffsetLength");
        Argument.assertNotLessThan(maxAbsStride, 1, "maxAbsStride");
        Argument.assertEquals(target.length(), actual.length(), "target.length()", "actual.length()");

        final StridedBooleanIla stridedTarget = StridedBooleanIlaFromBooleanIla.create(target, new boolean[10]);
        final StridedBooleanIla stridedActual = StridedBooleanIlaFromBooleanIla.create(target, new boolean[10]);
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
                            stridedTarget.get(targetBase, offset, stride, start, length);
                            stridedActual.get(actualBase, offset, stride, start, length);
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

    public static void dump(String msg, boolean[] array) {
        System.out.println(msg + ":");
        for (int ii = 0; ii < array.length; ++ii) {
            System.out.print(" " + array[ii]);
        }
        System.out.println();
    }
}
// AUTO GENERATED FROM TEMPLATE
