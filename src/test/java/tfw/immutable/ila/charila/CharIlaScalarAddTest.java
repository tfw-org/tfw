package tfw.immutable.ila.charila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.charila.CharIla;
import tfw.immutable.ila.charila.CharIlaFromArray;
import tfw.immutable.ila.charila.CharIlaScalarAdd;

/**
 *
 * @immutables.types=numeric
 */
public class CharIlaScalarAddTest extends TestCase
{
    public void testAll() throws Exception
    {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final char scalar = (char)random.nextInt();
        final char[] array = new char[length];
        final char[] target = new char[length];
        for(int ii = 0; ii < array.length; ++ii)
        {
            array[ii] = (char)random.nextInt();
            target[ii] = (char) (array[ii] + scalar);
        }
        CharIla ila = CharIlaFromArray.create(array);
        CharIla targetIla = CharIlaFromArray.create(target);
        CharIla actualIla = CharIlaScalarAdd.create(ila, scalar);
        final char epsilon = (char) 0.0;
        CharIlaCheck.checkAll(targetIla, actualIla,
                                IlaTestDimensions.defaultOffsetLength(),
                                IlaTestDimensions.defaultMaxStride(),
                                epsilon);
    }
}
// AUTO GENERATED FROM TEMPLATE
