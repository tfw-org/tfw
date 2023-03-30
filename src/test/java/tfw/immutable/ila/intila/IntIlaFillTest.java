package tfw.immutable.ila.intila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.intila.IntIla;
import tfw.immutable.ila.intila.IntIlaFromArray;
import tfw.immutable.ila.intila.IntIlaFill;

/**
 *
 * @immutables.types=all
 */
public class IntIlaFillTest extends TestCase
{
    public void testAll() throws Exception
    {
        final Random random = new Random(0);
        final int value = random.nextInt();
        final int length = IlaTestDimensions.defaultIlaLength();
        final int[] array = new int[length];
        for(int ii = 0; ii < array.length; ++ii)
        {
            array[ii] = value;
        }
        IntIla targetIla = IntIlaFromArray.create(array);
        IntIla actualIla = IntIlaFill.create(value, length);
        final int epsilon = 0;
        IntIlaCheck.checkAll(targetIla, actualIla,
                                  IlaTestDimensions.defaultOffsetLength(),
                                  IlaTestDimensions.defaultMaxStride(),
                                  epsilon);
    }
}
// AUTO GENERATED FROM TEMPLATE
