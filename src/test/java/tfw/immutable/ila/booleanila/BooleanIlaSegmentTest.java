package tfw.immutable.ila.booleanila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.booleanila.BooleanIla;
import tfw.immutable.ila.booleanila.BooleanIlaFromArray;
import tfw.immutable.ila.booleanila.BooleanIlaSegment;

/**
 *
 * @immutables.types=all
 */
public class BooleanIlaSegmentTest extends TestCase
{
    public void testAll() throws Exception
    {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final boolean[] master = new boolean[length];
        for(int ii = 0; ii < master.length; ++ii)
        {
            master[ii] = random.nextBoolean();
        }
        BooleanIla masterIla = BooleanIlaFromArray.create(master);
        BooleanIla checkIla = BooleanIlaSegment.create(masterIla, 0,
                                                         masterIla.length());
        final int offsetLength = IlaTestDimensions.defaultOffsetLength();
        final int maxStride = IlaTestDimensions.defaultMaxStride();
        final boolean epsilon = false;
        BooleanIlaCheck.checkWithoutCorrectness(checkIla, offsetLength,
                                                 epsilon);
        for(long start = 0; start < length; ++start)
        {
            for(long len = 0; len < length - start; ++len)
            {
                boolean[] array = new boolean[(int) len];
                for(int ii = 0; ii < array.length; ++ii)
                {
                    array[ii] = master[ii + (int) start];
                }
                BooleanIla targetIla = BooleanIlaFromArray.create(array);
                BooleanIla actualIla = BooleanIlaSegment.create(masterIla,
                                                                  start, len);
                BooleanIlaCheck.checkCorrectness(targetIla, actualIla,
                                                  offsetLength, maxStride,
                                                  epsilon);
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
