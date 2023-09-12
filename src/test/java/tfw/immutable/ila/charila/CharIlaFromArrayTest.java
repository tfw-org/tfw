package tfw.immutable.ila.charila;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class CharIlaFromArrayTest {
    @Test
    void testImmutabilityCheck() throws Exception {
        final int ilaLength = IlaTestDimensions.defaultIlaLength();
        final Random random = new Random(0);
        final char[] creation = new char[ilaLength];
        for (int ii = 0; ii < creation.length; ++ii) {
            creation[ii] = (char) random.nextInt();
        }
        final CharIla ila = CharIlaFromArray.create(creation);
        final int offsetLength = IlaTestDimensions.defaultOffsetLength();
        final char epsilon = (char) 0;
        CharIlaCheck.checkWithoutCorrectness(ila, offsetLength, epsilon);
    }

    @Test
    public void testValueCorrectness() throws Exception {
        final int ilaLength = IlaTestDimensions.defaultIlaLength();
        final int addlOffsetLength = IlaTestDimensions.defaultOffsetLength();
        final int maxAbsStride = IlaTestDimensions.defaultMaxStride();
        final Random random = new Random(0);
        final char[] creation = new char[ilaLength];
        for (int ii = 0; ii < creation.length; ++ii) {
            creation[ii] = (char) random.nextInt();
        }
        final CharIla ila = CharIlaFromArray.create(creation);
        final StridedCharIla stridedIla = new StridedCharIla(ila, new char[1000]);

        for (int stride = -maxAbsStride; stride <= maxAbsStride; ++stride) {
            if (stride != 0) {
                int absStride = stride < 0 ? -stride : stride;
                int offsetStart = stride < 0 ? (ilaLength - 1) * absStride : 0;
                int offsetEnd = offsetStart + addlOffsetLength;
                for (int offset = offsetStart; offset < offsetEnd; ++offset) {
                    final char[] arrayBase = new char[(ilaLength - 1) * absStride + 1 + addlOffsetLength];
                    final char[] ilaBase = new char[(ilaLength - 1) * absStride + 1 + addlOffsetLength];
                    for (int length = 1; length <= ilaLength; ++length) {
                        for (long start = 0; start < ilaLength - length + 1; ++start) {
                            for (int ii = 0; ii < arrayBase.length; ++ii) {
                                arrayBase[ii] = ilaBase[ii] = (char) random.nextInt();
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
