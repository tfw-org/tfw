package tfw.immutable.ila.floatila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.floatila.FloatIla;
import tfw.immutable.ila.floatila.FloatIlaFromArray;
import tfw.immutable.ila.floatila.FloatIlaMultiply;

/**
 *
 * @immutables.types=numeric
 */
public class FloatIlaMultiplyTest extends TestCase
{
    public void testAll() throws Exception
    {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final float[] leftArray = new float[length];
        final float[] rightArray = new float[length];
        final float[] array = new float[length];
        for(int ii = 0; ii < leftArray.length; ++ii)
        {
            leftArray[ii] = random.nextFloat();
            rightArray[ii] = random.nextFloat();
            array[ii] = (float) (leftArray[ii] * rightArray[ii]);
        }
        FloatIla leftIla = FloatIlaFromArray.create(leftArray);
        FloatIla rightIla = FloatIlaFromArray.create(rightArray);
        FloatIla targetIla = FloatIlaFromArray.create(array);
        FloatIla actualIla = FloatIlaMultiply.create(leftIla, rightIla);
        final float epsilon = (float) 0.0;
        FloatIlaCheck.checkAll(targetIla, actualIla,
                                IlaTestDimensions.defaultOffsetLength(),
                                IlaTestDimensions.defaultMaxStride(),
                                epsilon);
    }
}
// AUTO GENERATED FROM TEMPLATE