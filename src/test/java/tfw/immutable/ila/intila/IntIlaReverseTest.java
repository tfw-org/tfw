package tfw.immutable.ila.intila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.intila.IntIla;
import tfw.immutable.ila.intila.IntIlaFromArray;
import tfw.immutable.ila.intila.IntIlaReverse;

/**
 *
 * @immutables.types=all
 */
public class IntIlaReverseTest extends TestCase
{
    public void testAll() throws Exception
    {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final int[] array = new int[length];
        final int[] reversed = new int[length];
        for(int ii = 0; ii < array.length; ++ii)
        {
            array[ii] = random.nextInt();
        }
        for(int ii = 0; ii < reversed.length; ++ii)
        {
            reversed[ii] = array[length - 1 - ii];
        }
        IntIla origIla = IntIlaFromArray.create(array);
        IntIla targetIla = IntIlaFromArray.create(reversed);
        IntIla actualIla = IntIlaReverse.create(origIla);
        final int epsilon = 0;
        IntIlaCheck.checkAll(targetIla, actualIla,
                                IlaTestDimensions.defaultOffsetLength(),
                                IlaTestDimensions.defaultMaxStride(),
                                epsilon);
    }
}
// AUTO GENERATED FROM TEMPLATE
