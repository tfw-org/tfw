package tfw.immutable.ila.booleanila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.booleanila.BooleanIla;
import tfw.immutable.ila.booleanila.BooleanIlaFromArray;
import tfw.immutable.ila.booleanila.BooleanIlaReverse;

/**
 *
 * @immutables.types=all
 */
public class BooleanIlaReverseTest extends TestCase
{
    public void testAll() throws Exception
    {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final boolean[] array = new boolean[length];
        final boolean[] reversed = new boolean[length];
        for(int ii = 0; ii < array.length; ++ii)
        {
            array[ii] = random.nextBoolean();
        }
        for(int ii = 0; ii < reversed.length; ++ii)
        {
            reversed[ii] = array[length - 1 - ii];
        }
        BooleanIla origIla = BooleanIlaFromArray.create(array);
        BooleanIla targetIla = BooleanIlaFromArray.create(reversed);
        BooleanIla actualIla = BooleanIlaReverse.create(origIla);
        final boolean epsilon = false;
        BooleanIlaCheck.checkAll(targetIla, actualIla,
                                IlaTestDimensions.defaultOffsetLength(),
                                IlaTestDimensions.defaultMaxStride(),
                                epsilon);
    }
}
// AUTO GENERATED FROM TEMPLATE
