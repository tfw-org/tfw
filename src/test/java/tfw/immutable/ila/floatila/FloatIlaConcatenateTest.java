package tfw.immutable.ila.floatila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;

/**
 *
 * @immutables.types=all
 */
public class FloatIlaConcatenateTest extends TestCase {
    public void testAll() throws Exception {
        final Random random = new Random(0);
        final int leftLength = IlaTestDimensions.defaultIlaLength();
        final int rightLength = 1 + random.nextInt(leftLength);
        final float[] leftArray = new float[leftLength];
        final float[] rightArray = new float[rightLength];
        final float[] array = new float[leftLength + rightLength];
        for (int ii = 0; ii < leftArray.length; ++ii) {
            array[ii] = leftArray[ii] = random.nextFloat();
        }
        for (int ii = 0; ii < rightArray.length; ++ii) {
            array[ii + leftLength] = rightArray[ii] = random.nextFloat();
        }
        FloatIla leftIla = FloatIlaFromArray.create(leftArray);
        FloatIla rightIla = FloatIlaFromArray.create(rightArray);
        FloatIla targetIla = FloatIlaFromArray.create(array);
        FloatIla actualIla = FloatIlaConcatenate.create(leftIla, rightIla);
        final float epsilon = 0.0f;
        FloatIlaCheck.checkAll(
                targetIla,
                actualIla,
                IlaTestDimensions.defaultOffsetLength(),
                IlaTestDimensions.defaultMaxStride(),
                epsilon);
    }
}
// AUTO GENERATED FROM TEMPLATE
