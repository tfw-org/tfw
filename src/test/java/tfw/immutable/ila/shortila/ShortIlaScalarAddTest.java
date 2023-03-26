package tfw.immutable.ila.shortila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.shortila.ShortIla;
import tfw.immutable.ila.shortila.ShortIlaFromArray;
import tfw.immutable.ila.shortila.ShortIlaScalarAdd;

/**
 *
 * @immutables.types=numeric
 */
public class ShortIlaScalarAddTest extends TestCase
{
    public void testAll() throws Exception
    {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final short scalar = (short)random.nextInt();
        final short[] array = new short[length];
        final short[] target = new short[length];
        for(int ii = 0; ii < array.length; ++ii)
        {
            array[ii] = (short)random.nextInt();
            target[ii] = (short) (array[ii] + scalar);
        }
        ShortIla ila = ShortIlaFromArray.create(array);
        ShortIla targetIla = ShortIlaFromArray.create(target);
        ShortIla actualIla = ShortIlaScalarAdd.create(ila, scalar);
        final short epsilon = (short) 0.0;
        ShortIlaCheck.checkAll(targetIla, actualIla,
                                IlaTestDimensions.defaultOffsetLength(),
                                IlaTestDimensions.defaultMaxStride(),
                                epsilon);
    }
}
// AUTO GENERATED FROM TEMPLATE
