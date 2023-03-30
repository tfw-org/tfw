package tfw.immutable.ila.intila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.intila.IntIla;
import tfw.immutable.ila.intila.IntIlaFromArray;
import tfw.immutable.ila.intila.IntIlaSegment;

/**
 *
 * @immutables.types=all
 */
public class IntIlaSegmentTest extends TestCase
{
    public void testAll() throws Exception
    {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final int[] master = new int[length];
        for(int ii = 0; ii < master.length; ++ii)
        {
            master[ii] = random.nextInt();
        }
        IntIla masterIla = IntIlaFromArray.create(master);
        IntIla checkIla = IntIlaSegment.create(masterIla, 0,
                                                         masterIla.length());
        final int offsetLength = IlaTestDimensions.defaultOffsetLength();
        final int maxStride = IlaTestDimensions.defaultMaxStride();
        final int epsilon = 0;
        IntIlaCheck.checkWithoutCorrectness(checkIla, offsetLength,
                                                 epsilon);
        for(long start = 0; start < length; ++start)
        {
            for(long len = 0; len < length - start; ++len)
            {
                int[] array = new int[(int) len];
                for(int ii = 0; ii < array.length; ++ii)
                {
                    array[ii] = master[ii + (int) start];
                }
                IntIla targetIla = IntIlaFromArray.create(array);
                IntIla actualIla = IntIlaSegment.create(masterIla,
                                                                  start, len);
                IntIlaCheck.checkCorrectness(targetIla, actualIla,
                                                  offsetLength, maxStride,
                                                  epsilon);
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
