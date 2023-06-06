package tfw.immutable.ila.doubleila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;

/**
 *
 * @immutables.types=numeric
 */
public class DoubleIlaRampTest extends TestCase {
    public void testAll() throws Exception {
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
        final double epsilon = (double) 0.000001;
        DoubleIlaCheck.checkAll(
                targetIla,
                actualIla,
                IlaTestDimensions.defaultOffsetLength(),
                IlaTestDimensions.defaultMaxStride(),
                epsilon);
    }
}
// AUTO GENERATED FROM TEMPLATE
