package tfw.immutable.ila.longila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaFromArray;
import tfw.immutable.ila.longila.LongIlaNegate;

/**
 *
 * @immutables.types=numeric
 */
public class LongIlaNegateTest extends TestCase
{
    public void testAll() throws Exception
    {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final long[] array = new long[length];
        final long[] target = new long[length];
        for(int ii = 0; ii < array.length; ++ii)
        {
            array[ii] = random.nextLong();
            target[ii] = (long) -array[ii];
        }
        LongIla ila = LongIlaFromArray.create(array);
        LongIla targetIla = LongIlaFromArray.create(target);
        LongIla actualIla = LongIlaNegate.create(ila);
        final long epsilon = (long) 0.0;
        LongIlaCheck.checkAll(targetIla, actualIla,
                                IlaTestDimensions.defaultOffsetLength(),
                                IlaTestDimensions.defaultMaxStride(),
                                epsilon);
    }
}
// AUTO GENERATED FROM TEMPLATE