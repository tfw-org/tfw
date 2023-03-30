package tfw.immutable.ila.charila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.charila.CharIla;
import tfw.immutable.ila.charila.CharIlaFromArray;
import tfw.immutable.ila.charila.CharIlaDecimate;

/**
 *
 * @immutables.types=all
 */
public class CharIlaDecimateTest extends TestCase
{
    public void testAll() throws Exception
    {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final char[] array = new char[length];
        for(int ii = 0; ii < array.length; ++ii)
        {
            array[ii] = (char)random.nextInt();
        }
        CharIla ila = CharIlaFromArray.create(array);
        for(int factor = 2; factor <= length; ++factor)
        {
            final int targetLength = (length + factor - 1) / factor;
            final char[] target = new char[targetLength];
            for(int ii = 0; ii < target.length; ++ii)
            {
                target[ii] = array[ii * factor];
            }
            CharIla targetIla = CharIlaFromArray.create(target);
            CharIla actualIla = CharIlaDecimate.create(ila, factor);
            final char epsilon = (char)0;
            CharIlaCheck.checkAll(targetIla, actualIla,
                                    IlaTestDimensions.defaultOffsetLength(),
                                    IlaTestDimensions.defaultMaxStride(),
                                    epsilon);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
