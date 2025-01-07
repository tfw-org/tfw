package tfw.immutable.ila.floatila;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.util.Random;

public final class FloatIlaCheck {
    private FloatIlaCheck() {
        // non-instantiable class
    }

    public static void checkGetArguments(final FloatIla ila) throws IOException {
        final long ilaLength = ila.length();
        final float[] array = new float[10];

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
                .hasMessage("start (=" + ilaLength + ") >= ila.length() (=" + ilaLength + ") not allowed!");
        assertThatThrownBy(() -> ila.get(array, array.length - 1, 0, 2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("offset+length (=11) > array.length (=10) not allowed!");
        assertThatThrownBy(() -> ila.get(array, 0, ilaLength - 1, 2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("start+length (=" + (ilaLength + 1) + ") > ila.length() (=" + ilaLength + ") not allowed!");
    }

    public static void checkGetExhaustively(final FloatIla ila1, final FloatIla ila2) throws IOException {
        final int length1 = (int) Math.min(ila1.length(), Integer.MAX_VALUE);
        final int length2 = (int) Math.min(ila2.length(), Integer.MAX_VALUE);

        assertThat(length2).isEqualTo(length1);

        final float[] a1 = new float[length1];
        final float[] a2 = new float[length1];

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

    public static void check(final FloatIla expectedIla, final FloatIla actualIla) throws IOException {
        checkGetArguments(actualIla);
        checkGetExhaustively(expectedIla, actualIla);
    }

    public static void checkAll(
            final FloatIla target, final FloatIla actual, int addlOffsetLength, int maxAbsStride, float epsilon)
            throws Exception {
        checkGetArguments(actual);
        FloatIlaUtilCheck.checkAll(actual, epsilon);
        checkFourFiveEquivalence(actual, addlOffsetLength, epsilon);
        checkCorrectness(target, actual, addlOffsetLength, maxAbsStride, epsilon);
    }

    public static void checkWithoutCorrectness(FloatIla ila, int offsetLength, float epsilon) throws Exception {
        checkGetArguments(ila);
        FloatIlaUtilCheck.checkAll(ila, epsilon);
        checkFourFiveEquivalence(ila, offsetLength, epsilon);
    }

    public static void checkFourFiveEquivalence(FloatIla ila, int offsetLength, float epsilon) throws Exception {
        if (offsetLength < 0) throw new Exception("offsetLength < 0 not allowed");

        final StridedFloatIla stridedFloatIla = StridedFloatIlaFromFloatIla.create(ila, new float[1000]);
        final float eps = epsilon < 0.0 ? -epsilon : epsilon;
        final float neps = -eps;
        final Random random = new Random(0);
        final int ilaLength = ila.length() + offsetLength <= Integer.MAX_VALUE
                ? (int) ila.length()
                : Integer.MAX_VALUE - offsetLength;
        for (int offset = 0; offset < offsetLength; ++offset) {
            final float[] four = new float[ilaLength + offsetLength];
            final float[] five = new float[ilaLength + offsetLength];
            for (int length = 1; length <= ilaLength; ++length) {
                for (long start = 0; start < ilaLength - length + 1; ++start) {
                    for (int ii = 0; ii < four.length; ++ii) {
                        five[ii] = four[ii] = random.nextFloat();
                    }
                    ila.get(four, offset, start, length);
                    stridedFloatIla.get(five, offset, 1, start, length);
                    for (int ii = 0; ii < length; ++ii) {
                        float delta = four[ii] - five[ii];
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
            final FloatIla target, final FloatIla actual, int addlOffsetLength, int maxAbsStride, float epsilon)
            throws Exception {
        if (addlOffsetLength < 0) throw new Exception("addlOffsetLength < 0 not allowed");
        if (maxAbsStride < 1) throw new Exception("maxAbsStride < 1 not allowed");
        if (target.length() != actual.length()) throw new Exception("target.length() != actual.length()");

        final StridedFloatIla stridedTarget = StridedFloatIlaFromFloatIla.create(target, new float[1000]);
        final StridedFloatIla stridedActual = StridedFloatIlaFromFloatIla.create(target, new float[1000]);
        final float eps = epsilon < 0.0 ? -epsilon : epsilon;
        final float neps = -eps;
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
                    final float[] targetBase = new float[arraySize];
                    final float[] actualBase = new float[arraySize];
                    for (int length = 1; length <= ilaLength; ++length) {
                        for (long start = 0; start < ilaLength - length + 1; ++start) {
                            for (int ii = 0; ii < targetBase.length; ++ii) {
                                targetBase[ii] = actualBase[ii] = random.nextFloat();
                            }
                            stridedTarget.get(targetBase, offset, stride, start, length);
                            stridedActual.get(actualBase, offset, stride, start, length);
                            for (int ii = 0; ii < arraySize; ++ii) {
                                float delta = actualBase[ii] - targetBase[ii];
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

    public static void dump(String msg, float[] array) {
        System.out.println(msg + ":");
        for (int ii = 0; ii < array.length; ++ii) {
            System.out.print(" " + array[ii]);
        }
        System.out.println();
    }
}
// AUTO GENERATED FROM TEMPLATE
