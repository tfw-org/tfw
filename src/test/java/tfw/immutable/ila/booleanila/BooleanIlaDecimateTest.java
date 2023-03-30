package tfw.immutable.ila.booleanila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.booleanila.BooleanIla;
import tfw.immutable.ila.booleanila.BooleanIlaFromArray;
import tfw.immutable.ila.booleanila.BooleanIlaDecimate;

/**
 *
 * @immutables.types=all
 */
public class BooleanIlaDecimateTest extends TestCase
{
    public void testAll() throws Exception
    {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final boolean[] array = new boolean[length];
        for(int ii = 0; ii < array.length; ++ii)
        {
            array[ii] = random.nextBoolean();
        }
        BooleanIla ila = BooleanIlaFromArray.create(array);
        for(int factor = 2; factor <= length; ++factor)
        {
            final int targetLength = (length + factor - 1) / factor;
            final boolean[] target = new boolean[targetLength];
            for(int ii = 0; ii < target.length; ++ii)
            {
                target[ii] = array[ii * factor];
            }
            BooleanIla targetIla = BooleanIlaFromArray.create(target);
            BooleanIla actualIla = BooleanIlaDecimate.create(ila, factor);
            final boolean epsilon = false;
            BooleanIlaCheck.checkAll(targetIla, actualIla,
                                    IlaTestDimensions.defaultOffsetLength(),
                                    IlaTestDimensions.defaultMaxStride(),
                                    epsilon);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
