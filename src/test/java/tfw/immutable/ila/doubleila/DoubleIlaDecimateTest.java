package tfw.immutable.ila.doubleila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.doubleila.DoubleIlaFromArray;
import tfw.immutable.ila.doubleila.DoubleIlaDecimate;

/**
 *
 * @immutables.types=all
 */
public class DoubleIlaDecimateTest extends TestCase
{
    public void testAll() throws Exception
    {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final double[] array = new double[length];
        for(int ii = 0; ii < array.length; ++ii)
        {
            array[ii] = random.nextDouble();
        }
        DoubleIla ila = DoubleIlaFromArray.create(array);
        for(int factor = 2; factor <= length; ++factor)
        {
            final int targetLength = (length + factor - 1) / factor;
            final double[] target = new double[targetLength];
            for(int ii = 0; ii < target.length; ++ii)
            {
                target[ii] = array[ii * factor];
            }
            DoubleIla targetIla = DoubleIlaFromArray.create(target);
            DoubleIla actualIla = DoubleIlaDecimate.create(ila, factor);
            final double epsilon = 0.0;
            DoubleIlaCheck.checkAll(targetIla, actualIla,
                                    IlaTestDimensions.defaultOffsetLength(),
                                    IlaTestDimensions.defaultMaxStride(),
                                    epsilon);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
