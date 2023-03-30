package tfw.immutable.ila.doubleila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.doubleila.DoubleIlaFromArray;
import tfw.immutable.ila.doubleila.DoubleIlaFill;

/**
 *
 * @immutables.types=all
 */
public class DoubleIlaFillTest extends TestCase
{
    public void testAll() throws Exception
    {
        final Random random = new Random(0);
        final double value = random.nextDouble();
        final int length = IlaTestDimensions.defaultIlaLength();
        final double[] array = new double[length];
        for(int ii = 0; ii < array.length; ++ii)
        {
            array[ii] = value;
        }
        DoubleIla targetIla = DoubleIlaFromArray.create(array);
        DoubleIla actualIla = DoubleIlaFill.create(value, length);
        final double epsilon = 0.0;
        DoubleIlaCheck.checkAll(targetIla, actualIla,
                                  IlaTestDimensions.defaultOffsetLength(),
                                  IlaTestDimensions.defaultMaxStride(),
                                  epsilon);
    }
}
// AUTO GENERATED FROM TEMPLATE
