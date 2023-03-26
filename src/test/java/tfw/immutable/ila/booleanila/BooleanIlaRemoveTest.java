package tfw.immutable.ila.booleanila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.booleanila.BooleanIla;
import tfw.immutable.ila.booleanila.BooleanIlaFromArray;
import tfw.immutable.ila.booleanila.BooleanIlaRemove;

/**
 *
 * @immutables.types=all
 */
public class BooleanIlaRemoveTest extends TestCase
{
    public void testAll() throws Exception
    {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final boolean[] array = new boolean[length];
        final boolean[] target = new boolean[length-1];
        for(int index = 0; index < length; ++index)
        {
            int targetii = 0;
            for(int ii = 0; ii < array.length; ++ii)
            {
                array[ii] = random.nextBoolean();
                if(ii != index)
                {
                    target[targetii++] = array[ii];
                }
            }
            BooleanIla origIla = BooleanIlaFromArray.create(array);
            BooleanIla targetIla = BooleanIlaFromArray.create(target);
            BooleanIla actualIla = BooleanIlaRemove.create(origIla, index);
            final boolean epsilon = false;
            BooleanIlaCheck.checkAll(targetIla, actualIla,
                                      IlaTestDimensions.defaultOffsetLength(),
                                      IlaTestDimensions.defaultMaxStride(),
                                      epsilon);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
