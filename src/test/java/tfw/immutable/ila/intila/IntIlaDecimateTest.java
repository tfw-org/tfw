package tfw.immutable.ila.intila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.intila.IntIla;
import tfw.immutable.ila.intila.IntIlaFromArray;
import tfw.immutable.ila.intila.IntIlaDecimate;

/**
 *
 * @immutables.types=all
 */
public class IntIlaDecimateTest extends TestCase
{
    public void testAll() throws Exception
    {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final int[] array = new int[length];
        for(int ii = 0; ii < array.length; ++ii)
        {
            array[ii] = random.nextInt();
        }
        IntIla ila = IntIlaFromArray.create(array);
        for(int factor = 2; factor <= length; ++factor)
        {
            final int targetLength = (length + factor - 1) / factor;
            final int[] target = new int[targetLength];
            for(int ii = 0; ii < target.length; ++ii)
            {
                target[ii] = array[ii * factor];
            }
            IntIla targetIla = IntIlaFromArray.create(target);
            IntIla actualIla = IntIlaDecimate.create(ila, factor);
            final int epsilon = 0;
            IntIlaCheck.checkAll(targetIla, actualIla,
                                    IlaTestDimensions.defaultOffsetLength(),
                                    IlaTestDimensions.defaultMaxStride(),
                                    epsilon);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
