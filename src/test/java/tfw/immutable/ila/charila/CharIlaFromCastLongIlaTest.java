package tfw.immutable.ila.charila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.charila.CharIla;
import tfw.immutable.ila.charila.CharIlaFromArray;
import tfw.immutable.ila.charila.CharIlaFromCastLongIla;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaFromArray;

/**
 *
 * @immutables.types=numericnotlong
 */
public class CharIlaFromCastLongIlaTest extends TestCase
{
    public void testAll() throws Exception
    {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final long[] array = new long[length];
        final char[] target = new char[length];
        for(int ii = 0; ii < array.length; ++ii)
        {
            array[ii] = random.nextLong();
            target[ii] = (char) array[ii];
        }
        LongIla ila = LongIlaFromArray.create(array);
        CharIla targetIla = CharIlaFromArray.create(target);
        CharIla actualIla = CharIlaFromCastLongIla.create(ila);
        final char epsilon = (char) 0.0;
        CharIlaCheck.checkAll(targetIla, actualIla,
                                IlaTestDimensions.defaultOffsetLength(),
                                IlaTestDimensions.defaultMaxStride(),
                                epsilon);
    }
}
// AUTO GENERATED FROM TEMPLATE
