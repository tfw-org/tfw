package tfw.immutable.ila.longila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaFromArray;
import tfw.immutable.ila.longila.LongIlaRemove;

/**
 *
 * @immutables.types=all
 */
public class LongIlaRemoveTest extends TestCase
{
    public void testAll() throws Exception
    {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final long[] array = new long[length];
        final long[] target = new long[length-1];
        for(int index = 0; index < length; ++index)
        {
            int targetii = 0;
            for(int ii = 0; ii < array.length; ++ii)
            {
                array[ii] = random.nextLong();
                if(ii != index)
                {
                    target[targetii++] = array[ii];
                }
            }
            LongIla origIla = LongIlaFromArray.create(array);
            LongIla targetIla = LongIlaFromArray.create(target);
            LongIla actualIla = LongIlaRemove.create(origIla, index);
            final long epsilon = 0L;
            LongIlaCheck.checkAll(targetIla, actualIla,
                                      IlaTestDimensions.defaultOffsetLength(),
                                      IlaTestDimensions.defaultMaxStride(),
                                      epsilon);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
