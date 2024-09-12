package tfw.immutable.ila.doubleila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class DoubleIlaSegmentTest {
    @Test
    void testArguments() throws Exception {
        final DoubleIla ila = DoubleIlaFromArray.create(new double[10]);
        final long ilaLength = ila.length();

        assertThrows(IllegalArgumentException.class, () -> DoubleIlaSegment.create(null, 0));
        assertThrows(IllegalArgumentException.class, () -> DoubleIlaSegment.create(ila, -1));
        assertThrows(IllegalArgumentException.class, () -> DoubleIlaSegment.create(ila, ilaLength + 1));
        assertThrows(IllegalArgumentException.class, () -> DoubleIlaSegment.create(null, 0, 0));
        assertThrows(IllegalArgumentException.class, () -> DoubleIlaSegment.create(ila, -1, 0));
        assertThrows(IllegalArgumentException.class, () -> DoubleIlaSegment.create(ila, 0, -1));
        assertThrows(IllegalArgumentException.class, () -> DoubleIlaSegment.create(ila, ilaLength + 1, 0));
        assertThrows(IllegalArgumentException.class, () -> DoubleIlaSegment.create(ila, 0, ilaLength + 1));
    }

    @Test
    void testAll() throws Exception {
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
