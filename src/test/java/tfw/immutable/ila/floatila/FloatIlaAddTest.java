package tfw.immutable.ila.floatila;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

/**
 *
 * @immutables.types=numeric
 */
class FloatIlaAddTest {
    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final float[] leftArray = new float[length];
        final float[] rightArray = new float[length];
        final float[] array = new float[length];
        for (int ii = 0; ii < leftArray.length; ++ii) {
            leftArray[ii] = random.nextFloat();
            rightArray[ii] = random.nextFloat();
            array[ii] = (leftArray[ii] + rightArray[ii]);
        }
        FloatIla leftIla = FloatIlaFromArray.create(leftArray);
        FloatIla rightIla = FloatIlaFromArray.create(rightArray);
        FloatIla targetIla = FloatIlaFromArray.create(array);
        FloatIla actualIla = FloatIlaAdd.create(leftIla, rightIla);
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
