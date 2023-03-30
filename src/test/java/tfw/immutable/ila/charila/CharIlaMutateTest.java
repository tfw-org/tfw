package tfw.immutable.ila.charila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.charila.CharIla;
import tfw.immutable.ila.charila.CharIlaFromArray;
import tfw.immutable.ila.charila.CharIlaMutate;

/**
 *
 * @immutables.types=all
 */
public class CharIlaMutateTest extends TestCase
{
    public void testAll() throws Exception
    {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final char[] array = new char[length];
        final char[] target = new char[length];
        for(int index = 0; index < length; ++index)
        {
            for(int ii = 0; ii < array.length; ++ii)
            {
                array[ii] = target[ii] = (char)random.nextInt();
            }
            final char value = (char)random.nextInt();
            target[index] = value;
            CharIla origIla = CharIlaFromArray.create(array);
            CharIla targetIla = CharIlaFromArray.create(target);
            CharIla actualIla = CharIlaMutate.create(origIla, index,
                                                             value);
            final char epsilon = (char)0;
            CharIlaCheck.checkAll(targetIla, actualIla,
                                      IlaTestDimensions.defaultOffsetLength(),
                                      IlaTestDimensions.defaultMaxStride(),
                                      epsilon);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
