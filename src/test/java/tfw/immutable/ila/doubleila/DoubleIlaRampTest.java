package tfw.immutable.ila.doubleila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class DoubleIlaRampTest {
    @Test
    void testArguments() throws Exception {
        final Random random = new Random(0);
        final double start = random.nextDouble();
        final double increment = random.nextDouble();

        assertThrows(IllegalArgumentException.class, () -> DoubleIlaRamp.create(start, increment, -1));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final double startValue = random.nextDouble();
        final double increment = random.nextDouble();
        final int length = IlaTestDimensions.defaultIlaLength();
        final double[] array = new double[length];
        double value = startValue;
        for (int ii = 0; ii < array.length; ++ii, value += increment) {
            array[ii] = value;
        }
        DoubleIla targetIla = DoubleIlaFromArray.create(array);
        DoubleIla actualIla = DoubleIlaRamp.create(startValue, increment, length);
        final double epsilon = 0.000001;
        DoubleIlaCheck.checkAll(
                targetIla,
                actualIla,
                IlaTestDimensions.defaultOffsetLength(),
                IlaTestDimensions.defaultMaxStride(),
                epsilon);
    }
}
// AUTO GENERATED FROM TEMPLATE
