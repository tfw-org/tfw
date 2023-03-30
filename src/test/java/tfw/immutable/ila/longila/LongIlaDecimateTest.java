package tfw.immutable.ila.longila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaFromArray;
import tfw.immutable.ila.longila.LongIlaDecimate;

/**
 *
 * @immutables.types=all
 */
public class LongIlaDecimateTest extends TestCase
{
    public void testAll() throws Exception
    {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final long[] array = new long[length];
        for(int ii = 0; ii < array.length; ++ii)
        {
            array[ii] = random.nextLong();
        }
        LongIla ila = LongIlaFromArray.create(array);
        for(int factor = 2; factor <= length; ++factor)
        {
            final int targetLength = (length + factor - 1) / factor;
            final long[] target = new long[targetLength];
            for(int ii = 0; ii < target.length; ++ii)
            {
                target[ii] = array[ii * factor];
            }
            LongIla targetIla = LongIlaFromArray.create(target);
            LongIla actualIla = LongIlaDecimate.create(ila, factor);
            final long epsilon = 0L;
            LongIlaCheck.checkAll(targetIla, actualIla,
                                    IlaTestDimensions.defaultOffsetLength(),
                                    IlaTestDimensions.defaultMaxStride(),
                                    epsilon);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
