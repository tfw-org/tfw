package tfw.immutable.ila.charila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.charila.CharIla;
import tfw.immutable.ila.charila.CharIlaFromArray;
import tfw.immutable.ila.charila.CharIlaRemove;

/**
 *
 * @immutables.types=all
 */
public class CharIlaRemoveTest extends TestCase
{
    public void testAll() throws Exception
    {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final char[] array = new char[length];
        final char[] target = new char[length-1];
        for(int index = 0; index < length; ++index)
        {
            int targetii = 0;
            for(int ii = 0; ii < array.length; ++ii)
            {
                array[ii] = (char)random.nextInt();
                if(ii != index)
                {
                    target[targetii++] = array[ii];
                }
            }
            CharIla origIla = CharIlaFromArray.create(array);
            CharIla targetIla = CharIlaFromArray.create(target);
            CharIla actualIla = CharIlaRemove.create(origIla, index);
            final char epsilon = (char)0;
            CharIlaCheck.checkAll(targetIla, actualIla,
                                      IlaTestDimensions.defaultOffsetLength(),
                                      IlaTestDimensions.defaultMaxStride(),
                                      epsilon);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE