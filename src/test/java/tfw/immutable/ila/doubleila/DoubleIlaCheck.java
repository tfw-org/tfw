package tfw.immutable.ila.doubleila;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.util.Random;

public final class DoubleIlaCheck {
    private DoubleIlaCheck() {
        // non-instantiable class
    }

    public static void checkGetArguments(final DoubleIla ila) throws IOException {
        final long ilaLength = ila.length();
        final double[] array = new double[10];

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

    public static void checkGetExhaustively(final DoubleIla ila1, final DoubleIla ila2) throws IOException {
        final int length1 = (int) Math.min(ila1.length(), Integer.MAX_VALUE);
        final int length2 = (int) Math.min(ila2.length(), Integer.MAX_VALUE);

        assertThat(length2).isEqualTo(length1);

        final double[] a1 = new double[length1];
        final double[] a2 = new double[length1];

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

    public static void check(final DoubleIla expectedIla, final DoubleIla actualIla) throws IOException {
        checkGetArguments(actualIla);
        checkGetExhaustively(expectedIla, actualIla);
    }

    public static void checkAll(
            final DoubleIla target, final DoubleIla actual, int addlOffsetLength, int maxAbsStride, double epsilon)
            throws Exception {
        checkGetArguments(actual);
        DoubleIlaUtilCheck.checkAll(actual, epsilon);
        checkFourFiveEquivalence(actual, addlOffsetLength, epsilon);
        checkCorrectness(target, actual, addlOffsetLength, maxAbsStride, epsilon);
    }

    public static void checkWithoutCorrectness(DoubleIla ila, int offsetLength, double epsilon) throws Exception {
        checkGetArguments(ila);
        DoubleIlaUtilCheck.checkAll(ila, epsilon);
        checkFourFiveEquivalence(ila, offsetLength, epsilon);
    }

    public static void checkFourFiveEquivalence(DoubleIla ila, int offsetLength, double epsilon) throws Exception {
        if (offsetLength < 0) throw new Exception("offsetLength < 0 not allowed");

        final StridedDoubleIla stridedDoubleIla = StridedDoubleIlaFromDoubleIla.create(ila, new double[1000]);
        final double eps = epsilon < 0.0 ? -epsilon : epsilon;
        final double neps = -eps;
        final Random random = new Random(0);
        final int ilaLength = ila.length() + offsetLength <= Integer.MAX_VALUE
                ? (int) ila.length()
                : Integer.MAX_VALUE - offsetLength;
        for (int offset = 0; offset < offsetLength; ++offset) {
            final double[] four = new double[ilaLength + offsetLength];
            final double[] five = new double[ilaLength + offsetLength];
            for (int length = 1; length <= ilaLength; ++length) {
                for (long start = 0; start < ilaLength - length + 1; ++start) {
                    for (int ii = 0; ii < four.length; ++ii) {
                        five[ii] = four[ii] = random.nextDouble();
                    }
                    ila.get(four, offset, start, length);
                    stridedDoubleIla.get(five, offset, 1, start, length);
                    for (int ii = 0; ii < length; ++ii) {
                        double delta = four[ii] - five[ii];
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
            final DoubleIla target, final DoubleIla actual, int addlOffsetLength, int maxAbsStride, double epsilon)
            throws Exception {
        if (addlOffsetLength < 0) throw new Exception("addlOffsetLength < 0 not allowed");
        if (maxAbsStride < 1) throw new Exception("maxAbsStride < 1 not allowed");
        if (target.length() != actual.length()) throw new Exception("target.length() != actual.length()");

        final StridedDoubleIla stridedTarget = StridedDoubleIlaFromDoubleIla.create(target, new double[1000]);
        final StridedDoubleIla stridedActual = StridedDoubleIlaFromDoubleIla.create(target, new double[1000]);
        final double eps = epsilon < 0.0 ? -epsilon : epsilon;
        final double neps = -eps;
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
                    final double[] targetBase = new double[arraySize];
                    final double[] actualBase = new double[arraySize];
                    for (int length = 1; length <= ilaLength; ++length) {
                        for (long start = 0; start < ilaLength - length + 1; ++start) {
                            for (int ii = 0; ii < targetBase.length; ++ii) {
                                targetBase[ii] = actualBase[ii] = random.nextDouble();
                            }
                            stridedTarget.get(targetBase, offset, stride, start, length);
                            stridedActual.get(actualBase, offset, stride, start, length);
                            for (int ii = 0; ii < arraySize; ++ii) {
                                double delta = actualBase[ii] - targetBase[ii];
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

    public static void dump(String msg, double[] array) {
        System.out.println(msg + ":");
        for (int ii = 0; ii < array.length; ++ii) {
            System.out.print(" " + array[ii]);
        }
        System.out.println();
    }
}
// AUTO GENERATED FROM TEMPLATE
