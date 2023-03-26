package tfw.immutable.ila.longila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaFromArray;
import tfw.immutable.ila.longila.LongIlaSubtract;

/**
 *
 * @immutables.types=numeric
 */
public class LongIlaSubtractTest extends TestCase
{
    public void testAll() throws Exception
    {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final long[] leftArray = new long[length];
        final long[] rightArray = new long[length];
        final long[] array = new long[length];
        for(int ii = 0; ii < leftArray.length; ++ii)
        {
            leftArray[ii] = random.nextLong();
            rightArray[ii] = random.nextLong();
            array[ii] = (long) (leftArray[ii] - rightArray[ii]);
        }
        LongIla leftIla = LongIlaFromArray.create(leftArray);
        LongIla rightIla = LongIlaFromArray.create(rightArray);
        LongIla targetIla = LongIlaFromArray.create(array);
        LongIla actualIla = LongIlaSubtract.create(leftIla, rightIla);
        final long epsilon = (long) 0.0;
        LongIlaCheck.checkAll(targetIla, actualIla,
                                IlaTestDimensions.defaultOffsetLength(),
                                IlaTestDimensions.defaultMaxStride(),
                                epsilon);
    }
}
// AUTO GENERATED FROM TEMPLATE
