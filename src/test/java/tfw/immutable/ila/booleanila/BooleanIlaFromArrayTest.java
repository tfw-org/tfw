package tfw.immutable.ila.booleanila;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class BooleanIlaFromArrayTest {
    @Test
    void testImmutabilityCheck() throws Exception {
        final int ilaLength = IlaTestDimensions.defaultIlaLength();
        final Random random = new Random(0);
        final boolean[] creation = new boolean[ilaLength];
        for (int ii = 0; ii < creation.length; ++ii) {
            creation[ii] = random.nextBoolean();
        }
        final BooleanIla ila = BooleanIlaFromArray.create(creation);
        final int offsetLength = IlaTestDimensions.defaultOffsetLength();
        final boolean epsilon = false;
        BooleanIlaCheck.checkWithoutCorrectness(ila, offsetLength, epsilon);
    }

    @Test
    public void testValueCorrectness() throws Exception {
        final int ilaLength = IlaTestDimensions.defaultIlaLength();
        final int addlOffsetLength = IlaTestDimensions.defaultOffsetLength();
        final int maxAbsStride = IlaTestDimensions.defaultMaxStride();
        final Random random = new Random(0);
        final boolean[] creation = new boolean[ilaLength];
        for (int ii = 0; ii < creation.length; ++ii) {
            creation[ii] = random.nextBoolean();
        }
        final BooleanIla ila = BooleanIlaFromArray.create(creation);
        final StridedBooleanIla stridedIla = new StridedBooleanIla(ila, new boolean[1000]);

        for (int stride = -maxAbsStride; stride <= maxAbsStride; ++stride) {
            if (stride != 0) {
                int absStride = stride < 0 ? -stride : stride;
                int offsetStart = stride < 0 ? (ilaLength - 1) * absStride : 0;
                int offsetEnd = offsetStart + addlOffsetLength;
                for (int offset = offsetStart; offset < offsetEnd; ++offset) {
                    final boolean[] arrayBase = new boolean[(ilaLength - 1) * absStride + 1 + addlOffsetLength];
                    final boolean[] ilaBase = new boolean[(ilaLength - 1) * absStride + 1 + addlOffsetLength];
                    for (int length = 1; length <= ilaLength; ++length) {
                        for (long start = 0; start < ilaLength - length + 1; ++start) {
                            for (int ii = 0; ii < arrayBase.length; ++ii) {
                                arrayBase[ii] = ilaBase[ii] = random.nextBoolean();
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
