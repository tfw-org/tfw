package tfw.immutable.ila.intila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class IntIlaSegmentTest {
    @Test
    void argumentsTest() throws Exception {
        final IntIla ila = IntIlaFromArray.create(new int[10]);
        final long ilaLength = ila.length();

        assertThatThrownBy(() -> IntIlaSegment.create(null, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ila == null not allowed!");
        assertThatThrownBy(() -> IntIlaSegment.create(ila, -1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("start (=-1) < 0 not allowed!");
        assertThatThrownBy(() -> IntIlaSegment.create(ila, ilaLength + 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("length (=-1) < 0 not allowed!");
        assertThatThrownBy(() -> IntIlaSegment.create(null, 0, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ila == null not allowed!");
        assertThatThrownBy(() -> IntIlaSegment.create(ila, -1, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("start (=-1) < 0 not allowed!");
        assertThatThrownBy(() -> IntIlaSegment.create(ila, 0, -1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("length (=-1) < 0 not allowed!");
        assertThatThrownBy(() -> IntIlaSegment.create(ila, ilaLength + 1, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("start + length (=11) > ila.length() (=10) not allowed!");
        assertThatThrownBy(() -> IntIlaSegment.create(ila, 0, ilaLength + 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("start + length (=11) > ila.length() (=10) not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final int[] master = new int[length];
        for (int ii = 0; ii < master.length; ++ii) {
            master[ii] = random.nextInt();
        }
        IntIla masterIla = IntIlaFromArray.create(master);
        IntIla checkIla = IntIlaSegment.create(masterIla, 0, masterIla.length());
        final int offsetLength = IlaTestDimensions.defaultOffsetLength();
        final int maxStride = IlaTestDimensions.defaultMaxStride();
        final int epsilon = 0;
        IntIlaCheck.checkWithoutCorrectness(checkIla, offsetLength, epsilon);
        for (long start = 0; start < length; ++start) {
            for (long len = 0; len < length - start; ++len) {
                int[] array = new int[(int) len];
                for (int ii = 0; ii < array.length; ++ii) {
                    array[ii] = master[ii + (int) start];
                }
                IntIla targetIla = IntIlaFromArray.create(array);
                IntIla actualIla = IntIlaSegment.create(masterIla, start, len);
                IntIlaCheck.checkCorrectness(targetIla, actualIla, offsetLength, maxStride, epsilon);
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
