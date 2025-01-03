package tfw.immutable.ila.byteila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class ByteIlaFromArrayTest {
    @Test
    void argumentsTest() {
        assertThatThrownBy(() -> ByteIlaFromArray.create(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("array == null not allowed!");
    }

    @Test
    void immutabililtyTest() throws Exception {
        final int ilaLength = IlaTestDimensions.defaultIlaLength();
        final Random random = new Random(0);
        final byte[] creation = new byte[ilaLength];
        for (int ii = 0; ii < creation.length; ++ii) {
            creation[ii] = (byte) random.nextInt();
        }
        final ByteIla ila = ByteIlaFromArray.create(creation);
        final int offsetLength = IlaTestDimensions.defaultOffsetLength();
        final byte epsilon = (byte) 0;
        ByteIlaCheck.checkWithoutCorrectness(ila, offsetLength, epsilon);
    }

    @Test
    void valueCorrectnessTest() throws Exception {
        final int ilaLength = IlaTestDimensions.defaultIlaLength();
        final int addlOffsetLength = IlaTestDimensions.defaultOffsetLength();
        final int maxAbsStride = IlaTestDimensions.defaultMaxStride();
        final Random random = new Random(0);
        final byte[] creation = new byte[ilaLength];
        for (int ii = 0; ii < creation.length; ++ii) {
            creation[ii] = (byte) random.nextInt();
        }
        final ByteIla ila = ByteIlaFromArray.create(creation);
        final StridedByteIla stridedIla = StridedByteIlaFromByteIla.create(ila, new byte[1000]);

        for (int stride = -maxAbsStride; stride <= maxAbsStride; ++stride) {
            if (stride != 0) {
                int absStride = stride < 0 ? -stride : stride;
                int offsetStart = stride < 0 ? (ilaLength - 1) * absStride : 0;
                int offsetEnd = offsetStart + addlOffsetLength;
                for (int offset = offsetStart; offset < offsetEnd; ++offset) {
                    final byte[] arrayBase = new byte[(ilaLength - 1) * absStride + 1 + addlOffsetLength];
                    final byte[] ilaBase = new byte[(ilaLength - 1) * absStride + 1 + addlOffsetLength];
                    for (int length = 1; length <= ilaLength; ++length) {
                        for (long start = 0; start < ilaLength - length + 1; ++start) {
                            for (int ii = 0; ii < arrayBase.length; ++ii) {
                                arrayBase[ii] = ilaBase[ii] = (byte) random.nextInt();
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
