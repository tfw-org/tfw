package tfw.immutable.ila.doubleila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class DoubleIlaSegmentTest {
    @Test
    void argumentsTest() throws Exception {
        final DoubleIla ila = DoubleIlaFromArray.create(new double[10]);
        final long ilaLength = ila.length();

        assertThatThrownBy(() -> DoubleIlaSegment.create(null, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ila == null not allowed!");
        assertThatThrownBy(() -> DoubleIlaSegment.create(ila, -1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("start (=-1) < 0 not allowed!");
        assertThatThrownBy(() -> DoubleIlaSegment.create(ila, ilaLength + 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("length (=-1) < 0 not allowed!");
        assertThatThrownBy(() -> DoubleIlaSegment.create(null, 0, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ila == null not allowed!");
        assertThatThrownBy(() -> DoubleIlaSegment.create(ila, -1, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("start (=-1) < 0 not allowed!");
        assertThatThrownBy(() -> DoubleIlaSegment.create(ila, 0, -1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("length (=-1) < 0 not allowed!");
        assertThatThrownBy(() -> DoubleIlaSegment.create(ila, ilaLength + 1, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("start + length (=11) > ila.length() (=10) not allowed!");
        assertThatThrownBy(() -> DoubleIlaSegment.create(ila, 0, ilaLength + 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("start + length (=11) > ila.length() (=10) not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final double[] master = new double[length];
        for (int ii = 0; ii < master.length; ++ii) {
            master[ii] = random.nextDouble();
        }
        DoubleIla masterIla = DoubleIlaFromArray.create(master);
        DoubleIla checkIla = DoubleIlaSegment.create(masterIla, 0, masterIla.length());
        final int offsetLength = IlaTestDimensions.defaultOffsetLength();
        final int maxStride = IlaTestDimensions.defaultMaxStride();
        final double epsilon = 0.0;
        DoubleIlaCheck.checkWithoutCorrectness(checkIla, offsetLength, epsilon);
        for (long start = 0; start < length; ++start) {
            for (long len = 0; len < length - start; ++len) {
                double[] array = new double[(int) len];
                for (int ii = 0; ii < array.length; ++ii) {
                    array[ii] = master[ii + (int) start];
                }
                DoubleIla targetIla = DoubleIlaFromArray.create(array);
                DoubleIla actualIla = DoubleIlaSegment.create(masterIla, start, len);
                DoubleIlaCheck.checkCorrectness(targetIla, actualIla, offsetLength, maxStride, epsilon);
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
