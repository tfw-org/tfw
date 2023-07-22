package tfw.immutable.ila.doubleila;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class DoubleIlaSegmentTest {
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
