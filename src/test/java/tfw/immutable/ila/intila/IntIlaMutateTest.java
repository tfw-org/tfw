package tfw.immutable.ila.intila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.intila.IntIla;
import tfw.immutable.ila.intila.IntIlaFromArray;
import tfw.immutable.ila.intila.IntIlaMutate;

/**
 *
 * @immutables.types=all
 */
public class IntIlaMutateTest extends TestCase
{
    public void testAll() throws Exception
    {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final int[] array = new int[length];
        final int[] target = new int[length];
        for(int index = 0; index < length; ++index)
        {
            for(int ii = 0; ii < array.length; ++ii)
            {
                array[ii] = target[ii] = random.nextInt();
            }
            final int value = random.nextInt();
            target[index] = value;
            IntIla origIla = IntIlaFromArray.create(array);
            IntIla targetIla = IntIlaFromArray.create(target);
            IntIla actualIla = IntIlaMutate.create(origIla, index,
                                                             value);
            final int epsilon = 0;
            IntIlaCheck.checkAll(targetIla, actualIla,
                                      IlaTestDimensions.defaultOffsetLength(),
                                      IlaTestDimensions.defaultMaxStride(),
                                      epsilon);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
