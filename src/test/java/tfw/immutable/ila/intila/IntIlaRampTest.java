package tfw.immutable.ila.intila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.intila.IntIla;
import tfw.immutable.ila.intila.IntIlaFromArray;
import tfw.immutable.ila.intila.IntIlaRamp;

/**
 *
 * @immutables.types=numeric
 */
public class IntIlaRampTest extends TestCase
{
    public void testAll() throws Exception
    {
        final Random random = new Random(0);
        final int startValue = random.nextInt();
        final int increment = random.nextInt();
        final int length = IlaTestDimensions.defaultIlaLength();
        final int[] array = new int[length];
        int value = startValue;
        for(int ii = 0; ii < array.length; ++ii, value += increment)
        {
            array[ii] = value;
        }
        IntIla targetIla = IntIlaFromArray.create(array);
        IntIla actualIla = IntIlaRamp.create(startValue, increment,
                                                       length);
        final int epsilon = (int) 0.000001;
        IntIlaCheck.checkAll(targetIla, actualIla,
                                  IlaTestDimensions.defaultOffsetLength(),
                                  IlaTestDimensions.defaultMaxStride(),
                                  epsilon);
    }
}
// AUTO GENERATED FROM TEMPLATE
