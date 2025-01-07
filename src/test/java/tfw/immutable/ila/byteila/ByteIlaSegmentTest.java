package tfw.immutable.ila.byteila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class ByteIlaSegmentTest {
    @Test
    void argumentsTest() throws Exception {
        final ByteIla ila = ByteIlaFromArray.create(new byte[10]);
        final long ilaLength = ila.length();

        assertThatThrownBy(() -> ByteIlaSegment.create(null, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ila == null not allowed!");
        assertThatThrownBy(() -> ByteIlaSegment.create(ila, -1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("start (=-1) < 0 not allowed!");
        assertThatThrownBy(() -> ByteIlaSegment.create(ila, ilaLength + 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("length (=-1) < 0 not allowed!");
        assertThatThrownBy(() -> ByteIlaSegment.create(null, 0, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ila == null not allowed!");
        assertThatThrownBy(() -> ByteIlaSegment.create(ila, -1, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("start (=-1) < 0 not allowed!");
        assertThatThrownBy(() -> ByteIlaSegment.create(ila, 0, -1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("length (=-1) < 0 not allowed!");
        assertThatThrownBy(() -> ByteIlaSegment.create(ila, ilaLength + 1, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("start + length (=11) > ila.length() (=10) not allowed!");
        assertThatThrownBy(() -> ByteIlaSegment.create(ila, 0, ilaLength + 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("start + length (=11) > ila.length() (=10) not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final byte[] master = new byte[length];
        for (int ii = 0; ii < master.length; ++ii) {
            master[ii] = (byte) random.nextInt();
        }
        ByteIla masterIla = ByteIlaFromArray.create(master);
        ByteIla checkIla = ByteIlaSegment.create(masterIla, 0, masterIla.length());
        final int offsetLength = IlaTestDimensions.defaultOffsetLength();
        final int maxStride = IlaTestDimensions.defaultMaxStride();
        final byte epsilon = (byte) 0;
        ByteIlaCheck.checkWithoutCorrectness(checkIla, offsetLength, epsilon);
        for (long start = 0; start < length; ++start) {
            for (long len = 0; len < length - start; ++len) {
                byte[] array = new byte[(int) len];
                for (int ii = 0; ii < array.length; ++ii) {
                    array[ii] = master[ii + (int) start];
                }
                ByteIla targetIla = ByteIlaFromArray.create(array);
                ByteIla actualIla = ByteIlaSegment.create(masterIla, start, len);
                ByteIlaCheck.checkCorrectness(targetIla, actualIla, offsetLength, maxStride, epsilon);
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
