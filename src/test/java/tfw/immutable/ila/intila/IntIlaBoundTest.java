package tfw.immutable.ila.intila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.intila.IntIla;
import tfw.immutable.ila.intila.IntIlaFromArray;
import tfw.immutable.ila.intila.IntIlaBound;

/**
 *
 * @immutables.types=numeric
 */
public class IntIlaBoundTest extends TestCase
{
    public void testAll() throws Exception
    {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final int[] array = new int[length];
        final int[] target = new int[length];
        int minimum = random.nextInt();
        int maximum = random.nextInt();
        if(minimum > maximum)
        {
		int tmp = minimum;
		minimum = maximum;
		maximum = tmp;
        }
        for(int ii = 0; ii < array.length; ++ii)
        {
            array[ii] = random.nextInt();
            target[ii] = array[ii];
            if(target[ii] < minimum)
            {
                target[ii] = minimum;
            }
            else if(target[ii] > maximum)
            {
                target[ii] = maximum;
            }
        }
        IntIla ila = IntIlaFromArray.create(array);
        IntIla targetIla = IntIlaFromArray.create(target);
        IntIla actualIla = IntIlaBound.create(ila, minimum, maximum);
        final int epsilon = (int) 0.0;
        IntIlaCheck.checkAll(targetIla, actualIla,
                                IlaTestDimensions.defaultOffsetLength(),
                                IlaTestDimensions.defaultMaxStride(),
                                epsilon);
    }
}
// AUTO GENERATED FROM TEMPLATE