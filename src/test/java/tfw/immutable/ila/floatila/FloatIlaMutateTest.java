package tfw.immutable.ila.floatila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;

/**
 *
 * @immutables.types=all
 */
public class FloatIlaMutateTest extends TestCase {
    public void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final float[] array = new float[length];
        final float[] target = new float[length];
        for (int index = 0; index < length; ++index) {
            for (int ii = 0; ii < array.length; ++ii) {
                array[ii] = target[ii] = random.nextFloat();
            }
            final float value = random.nextFloat();
            target[index] = value;
            FloatIla origIla = FloatIlaFromArray.create(array);
            FloatIla targetIla = FloatIlaFromArray.create(target);
            FloatIla actualIla = FloatIlaMutate.create(origIla, index, value);
            final float epsilon = 0.0f;
            FloatIlaCheck.checkAll(
                    targetIla,
                    actualIla,
                    IlaTestDimensions.defaultOffsetLength(),
                    IlaTestDimensions.defaultMaxStride(),
                    epsilon);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
