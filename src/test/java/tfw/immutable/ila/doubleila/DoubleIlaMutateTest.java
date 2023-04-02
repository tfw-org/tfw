package tfw.immutable.ila.doubleila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.doubleila.DoubleIlaFromArray;
import tfw.immutable.ila.doubleila.DoubleIlaMutate;

/**
 *
 * @immutables.types=all
 */
public class DoubleIlaMutateTest extends TestCase
{
    public void testAll() throws Exception
    {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final double[] array = new double[length];
        final double[] target = new double[length];
        for(int index = 0; index < length; ++index)
        {
            for(int ii = 0; ii < array.length; ++ii)
            {
                array[ii] = target[ii] = random.nextDouble();
            }
            final double value = random.nextDouble();
            target[index] = value;
            DoubleIla origIla = DoubleIlaFromArray.create(array);
            DoubleIla targetIla = DoubleIlaFromArray.create(target);
            DoubleIla actualIla = DoubleIlaMutate.create(origIla, index,
                                                             value);
            final double epsilon = 0.0;
            DoubleIlaCheck.checkAll(targetIla, actualIla,
                                      IlaTestDimensions.defaultOffsetLength(),
                                      IlaTestDimensions.defaultMaxStride(),
                                      epsilon);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE