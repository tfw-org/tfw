package tfw.immutable.ila.floatila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.floatila.FloatIla;
import tfw.immutable.ila.floatila.FloatIlaFromArray;
import tfw.immutable.ila.floatila.FloatIlaFill;

/**
 *
 * @immutables.types=all
 */
public class FloatIlaFillTest extends TestCase
{
    public void testAll() throws Exception
    {
        final Random random = new Random(0);
        final float value = random.nextFloat();
        final int length = IlaTestDimensions.defaultIlaLength();
        final float[] array = new float[length];
        for(int ii = 0; ii < array.length; ++ii)
        {
            array[ii] = value;
        }
        FloatIla targetIla = FloatIlaFromArray.create(array);
        FloatIla actualIla = FloatIlaFill.create(value, length);
        final float epsilon = 0.0f;
        FloatIlaCheck.checkAll(targetIla, actualIla,
                                  IlaTestDimensions.defaultOffsetLength(),
                                  IlaTestDimensions.defaultMaxStride(),
                                  epsilon);
    }
}
// AUTO GENERATED FROM TEMPLATE
