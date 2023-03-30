package tfw.immutable.ila.longila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaFromArray;
import tfw.immutable.ila.longila.LongIlaFill;

/**
 *
 * @immutables.types=all
 */
public class LongIlaFillTest extends TestCase
{
    public void testAll() throws Exception
    {
        final Random random = new Random(0);
        final long value = random.nextLong();
        final int length = IlaTestDimensions.defaultIlaLength();
        final long[] array = new long[length];
        for(int ii = 0; ii < array.length; ++ii)
        {
            array[ii] = value;
        }
        LongIla targetIla = LongIlaFromArray.create(array);
        LongIla actualIla = LongIlaFill.create(value, length);
        final long epsilon = 0L;
        LongIlaCheck.checkAll(targetIla, actualIla,
                                  IlaTestDimensions.defaultOffsetLength(),
                                  IlaTestDimensions.defaultMaxStride(),
                                  epsilon);
    }
}
// AUTO GENERATED FROM TEMPLATE
