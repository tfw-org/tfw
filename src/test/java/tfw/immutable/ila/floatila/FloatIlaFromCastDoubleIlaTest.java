package tfw.immutable.ila.floatila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.floatila.FloatIla;
import tfw.immutable.ila.floatila.FloatIlaFromArray;
import tfw.immutable.ila.floatila.FloatIlaFromCastDoubleIla;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.doubleila.DoubleIlaFromArray;

/**
 *
 * @immutables.types=numericnotdouble
 */
public class FloatIlaFromCastDoubleIlaTest extends TestCase
{
    public void testAll() throws Exception
    {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final double[] array = new double[length];
        final float[] target = new float[length];
        for(int ii = 0; ii < array.length; ++ii)
        {
            array[ii] = random.nextDouble();
            target[ii] = (float) array[ii];
        }
        DoubleIla ila = DoubleIlaFromArray.create(array);
        FloatIla targetIla = FloatIlaFromArray.create(target);
        FloatIla actualIla = FloatIlaFromCastDoubleIla.create(ila);
        final float epsilon = (float) 0.0;
        FloatIlaCheck.checkAll(targetIla, actualIla,
                                IlaTestDimensions.defaultOffsetLength(),
                                IlaTestDimensions.defaultMaxStride(),
                                epsilon);
    }
}
// AUTO GENERATED FROM TEMPLATE
