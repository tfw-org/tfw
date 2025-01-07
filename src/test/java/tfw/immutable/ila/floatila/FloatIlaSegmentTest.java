package tfw.immutable.ila.floatila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class FloatIlaSegmentTest {
    @Test
    void argumentsTest() throws Exception {
        final FloatIla ila = FloatIlaFromArray.create(new float[10]);
        final long ilaLength = ila.length();

        assertThatThrownBy(() -> FloatIlaSegment.create(null, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ila == null not allowed!");
        assertThatThrownBy(() -> FloatIlaSegment.create(ila, -1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("start (=-1) < 0 not allowed!");
        assertThatThrownBy(() -> FloatIlaSegment.create(ila, ilaLength + 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("length (=-1) < 0 not allowed!");
        assertThatThrownBy(() -> FloatIlaSegment.create(null, 0, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ila == null not allowed!");
        assertThatThrownBy(() -> FloatIlaSegment.create(ila, -1, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("start (=-1) < 0 not allowed!");
        assertThatThrownBy(() -> FloatIlaSegment.create(ila, 0, -1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("length (=-1) < 0 not allowed!");
        assertThatThrownBy(() -> FloatIlaSegment.create(ila, ilaLength + 1, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("start + length (=11) > ila.length() (=10) not allowed!");
        assertThatThrownBy(() -> FloatIlaSegment.create(ila, 0, ilaLength + 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("start + length (=11) > ila.length() (=10) not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final float[] master = new float[length];
        for (int ii = 0; ii < master.length; ++ii) {
            master[ii] = random.nextFloat();
        }
        FloatIla masterIla = FloatIlaFromArray.create(master);
        FloatIla checkIla = FloatIlaSegment.create(masterIla, 0, masterIla.length());
        final int offsetLength = IlaTestDimensions.defaultOffsetLength();
        final int maxStride = IlaTestDimensions.defaultMaxStride();
        final float epsilon = 0.0f;
        FloatIlaCheck.checkWithoutCorrectness(checkIla, offsetLength, epsilon);
        for (long start = 0; start < length; ++start) {
            for (long len = 0; len < length - start; ++len) {
                float[] array = new float[(int) len];
                for (int ii = 0; ii < array.length; ++ii) {
                    array[ii] = master[ii + (int) start];
                }
                FloatIla targetIla = FloatIlaFromArray.create(array);
                FloatIla actualIla = FloatIlaSegment.create(masterIla, start, len);
                FloatIlaCheck.checkCorrectness(targetIla, actualIla, offsetLength, maxStride, epsilon);
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
