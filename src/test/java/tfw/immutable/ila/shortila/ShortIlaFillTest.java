package tfw.immutable.ila.shortila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.shortila.ShortIla;
import tfw.immutable.ila.shortila.ShortIlaFromArray;
import tfw.immutable.ila.shortila.ShortIlaFill;

/**
 *
 * @immutables.types=all
 */
public class ShortIlaFillTest extends TestCase
{
    public void testAll() throws Exception
    {
        final Random random = new Random(0);
        final short value = (short)random.nextInt();
        final int length = IlaTestDimensions.defaultIlaLength();
        final short[] array = new short[length];
        for(int ii = 0; ii < array.length; ++ii)
        {
            array[ii] = value;
        }
        ShortIla targetIla = ShortIlaFromArray.create(array);
        ShortIla actualIla = ShortIlaFill.create(value, length);
        final short epsilon = (short)0;
        ShortIlaCheck.checkAll(targetIla, actualIla,
                                  IlaTestDimensions.defaultOffsetLength(),
                                  IlaTestDimensions.defaultMaxStride(),
                                  epsilon);
    }
}
// AUTO GENERATED FROM TEMPLATE
