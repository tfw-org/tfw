package tfw.immutable.ila.floatila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class FloatIlaFromArrayTest {
    @Test
    void argumentsTest() {
        assertThatThrownBy(() -> FloatIlaFromArray.create(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("array == null not allowed!");
    }

    @Test
    void immutabililtyTest() throws Exception {
        final int ilaLength = IlaTestDimensions.defaultIlaLength();
        final Random random = new Random(0);
        final float[] creation = new float[ilaLength];
        for (int ii = 0; ii < creation.length; ++ii) {
            creation[ii] = random.nextFloat();
        }
        final FloatIla ila = FloatIlaFromArray.create(creation);
        final int offsetLength = IlaTestDimensions.defaultOffsetLength();
        final float epsilon = 0.0f;
        FloatIlaCheck.checkWithoutCorrectness(ila, offsetLength, epsilon);
    }

    @Test
    void valueCorrectnessTest() throws Exception {
        final int ilaLength = IlaTestDimensions.defaultIlaLength();
        final int addlOffsetLength = IlaTestDimensions.defaultOffsetLength();
        final int maxAbsStride = IlaTestDimensions.defaultMaxStride();
        final Random random = new Random(0);
        final float[] creation = new float[ilaLength];
        for (int ii = 0; ii < creation.length; ++ii) {
            creation[ii] = random.nextFloat();
        }
        final FloatIla ila = FloatIlaFromArray.create(creation);
        final StridedFloatIla stridedIla = StridedFloatIlaFromFloatIla.create(ila, new float[1000]);

        for (int stride = -maxAbsStride; stride <= maxAbsStride; ++stride) {
            if (stride != 0) {
                int absStride = stride < 0 ? -stride : stride;
                int offsetStart = stride < 0 ? (ilaLength - 1) * absStride : 0;
                int offsetEnd = offsetStart + addlOffsetLength;
                for (int offset = offsetStart; offset < offsetEnd; ++offset) {
                    final float[] arrayBase = new float[(ilaLength - 1) * absStride + 1 + addlOffsetLength];
                    final float[] ilaBase = new float[(ilaLength - 1) * absStride + 1 + addlOffsetLength];
                    for (int length = 1; length <= ilaLength; ++length) {
                        for (long start = 0; start < ilaLength - length + 1; ++start) {
                            for (int ii = 0; ii < arrayBase.length; ++ii) {
                                arrayBase[ii] = ilaBase[ii] = random.nextFloat();
                            }
                            for (int ii = (int) start, index = offset; ii < start + length; ++ii, index += stride) {
                                arrayBase[index] = creation[ii];
                            }
                            stridedIla.get(ilaBase, offset, stride, start, length);
                            for (int ii = 0; ii < arrayBase.length; ++ii) {
                                if (arrayBase[ii] != ilaBase[ii])
                                    throw new Exception("actual[" + ii + "] ("
                                            + ilaBase[ii]
                                            + ") !~ target["
                                            + ii + "] ("
                                            + arrayBase[ii]
                                            + ") {length="
                                            + length
                                            + ",start=" + start
                                            + ",offset=" + offset
                                            + ",stride=" + stride
                                            + "}");
                            }
                        }
                    }
                }
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
