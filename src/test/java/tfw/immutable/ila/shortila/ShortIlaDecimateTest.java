package tfw.immutable.ila.shortila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.shortila.ShortIla;
import tfw.immutable.ila.shortila.ShortIlaFromArray;
import tfw.immutable.ila.shortila.ShortIlaDecimate;

/**
 *
 * @immutables.types=all
 */
public class ShortIlaDecimateTest extends TestCase
{
    public void testAll() throws Exception
    {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final short[] array = new short[length];
        for(int ii = 0; ii < array.length; ++ii)
        {
            array[ii] = (short)random.nextInt();
        }
        ShortIla ila = ShortIlaFromArray.create(array);
        for(int factor = 2; factor <= length; ++factor)
        {
            final int targetLength = (length + factor - 1) / factor;
            final short[] target = new short[targetLength];
            for(int ii = 0; ii < target.length; ++ii)
            {
                target[ii] = array[ii * factor];
            }
            ShortIla targetIla = ShortIlaFromArray.create(target);
            ShortIla actualIla = ShortIlaDecimate.create(ila, factor);
            final short epsilon = (short)0;
            ShortIlaCheck.checkAll(targetIla, actualIla,
                                    IlaTestDimensions.defaultOffsetLength(),
                                    IlaTestDimensions.defaultMaxStride(),
                                    epsilon);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
