package tfw.immutable.ila.floatila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;

/**
 *
 * @immutables.types=numeric
 */
public class FloatIlaRampTest extends TestCase {
    public void testAll() throws Exception {
        final Random random = new Random(0);
        final float startValue = random.nextFloat();
        final float increment = random.nextFloat();
        final int length = IlaTestDimensions.defaultIlaLength();
        final float[] array = new float[length];
        float value = startValue;
        for (int ii = 0; ii < array.length; ++ii, value += increment) {
            array[ii] = value;
        }
        FloatIla targetIla = FloatIlaFromArray.create(array);
        FloatIla actualIla = FloatIlaRamp.create(startValue, increment, length);
        final float epsilon = (float) 0.000001;
        FloatIlaCheck.checkAll(
                targetIla,
                actualIla,
                IlaTestDimensions.defaultOffsetLength(),
                IlaTestDimensions.defaultMaxStride(),
                epsilon);
    }
}
// AUTO GENERATED FROM TEMPLATE
