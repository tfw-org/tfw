package tfw.immutable.ila.intila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.intila.IntIla;
import tfw.immutable.ila.intila.IntIlaFromArray;
import tfw.immutable.ila.intila.IntIlaScalarMultiply;

/**
 *
 * @immutables.types=numeric
 */
public class IntIlaScalarMultiplyTest extends TestCase
{
    public void testAll() throws Exception
    {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final int scalar = random.nextInt();
        final int[] array = new int[length];
        final int[] target = new int[length];
        for(int ii = 0; ii < array.length; ++ii)
        {
            array[ii] = random.nextInt();
            target[ii] = (int) (array[ii] * scalar);
        }
        IntIla ila = IntIlaFromArray.create(array);
        IntIla targetIla = IntIlaFromArray.create(target);
        IntIla actualIla = IntIlaScalarMultiply.create(ila, scalar);
        final int epsilon = (int) 0.0;
        IntIlaCheck.checkAll(targetIla, actualIla,
                                IlaTestDimensions.defaultOffsetLength(),
                                IlaTestDimensions.defaultMaxStride(),
                                epsilon);
    }
}
// AUTO GENERATED FROM TEMPLATE
