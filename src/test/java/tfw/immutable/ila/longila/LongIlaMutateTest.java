package tfw.immutable.ila.longila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaFromArray;
import tfw.immutable.ila.longila.LongIlaMutate;

/**
 *
 * @immutables.types=all
 */
public class LongIlaMutateTest extends TestCase
{
    public void testAll() throws Exception
    {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final long[] array = new long[length];
        final long[] target = new long[length];
        for(int index = 0; index < length; ++index)
        {
            for(int ii = 0; ii < array.length; ++ii)
            {
                array[ii] = target[ii] = random.nextLong();
            }
            final long value = random.nextLong();
            target[index] = value;
            LongIla origIla = LongIlaFromArray.create(array);
            LongIla targetIla = LongIlaFromArray.create(target);
            LongIla actualIla = LongIlaMutate.create(origIla, index,
                                                             value);
            final long epsilon = 0L;
            LongIlaCheck.checkAll(targetIla, actualIla,
                                      IlaTestDimensions.defaultOffsetLength(),
                                      IlaTestDimensions.defaultMaxStride(),
                                      epsilon);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
