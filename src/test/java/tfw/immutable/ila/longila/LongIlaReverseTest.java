package tfw.immutable.ila.longila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaFromArray;
import tfw.immutable.ila.longila.LongIlaReverse;

/**
 *
 * @immutables.types=all
 */
public class LongIlaReverseTest extends TestCase
{
    public void testAll() throws Exception
    {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final long[] array = new long[length];
        final long[] reversed = new long[length];
        for(int ii = 0; ii < array.length; ++ii)
        {
            array[ii] = random.nextLong();
        }
        for(int ii = 0; ii < reversed.length; ++ii)
        {
            reversed[ii] = array[length - 1 - ii];
        }
        LongIla origIla = LongIlaFromArray.create(array);
        LongIla targetIla = LongIlaFromArray.create(reversed);
        LongIla actualIla = LongIlaReverse.create(origIla);
        final long epsilon = 0L;
        LongIlaCheck.checkAll(targetIla, actualIla,
                                IlaTestDimensions.defaultOffsetLength(),
                                IlaTestDimensions.defaultMaxStride(),
                                epsilon);
    }
}
// AUTO GENERATED FROM TEMPLATE
