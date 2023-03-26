package tfw.immutable.ila.objectila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.objectila.ObjectIla;
import tfw.immutable.ila.objectila.ObjectIlaFromArray;
import tfw.immutable.ila.objectila.ObjectIlaConcatenate;

/**
 *
 * @immutables.types=all
 */
public class ObjectIlaConcatenateTest extends TestCase
{
    public void testAll() throws Exception
    {
        final Random random = new Random(0);
        final int leftLength = IlaTestDimensions.defaultIlaLength();
        final int rightLength = 1 + random.nextInt(leftLength);
        final Object[] leftArray = new Object[leftLength];
        final Object[] rightArray = new Object[rightLength];
        final Object[] array = new Object[leftLength + rightLength];
        for(int ii = 0; ii < leftArray.length; ++ii)
        {
            array[ii] = leftArray[ii] = new Object();
        }
        for(int ii = 0; ii < rightArray.length; ++ii)
        {
            array[ii + leftLength] = rightArray[ii] = new Object();
        }
        ObjectIla leftIla = ObjectIlaFromArray.create(leftArray);
        ObjectIla rightIla = ObjectIlaFromArray.create(rightArray);
        ObjectIla targetIla = ObjectIlaFromArray.create(array);
        ObjectIla actualIla = ObjectIlaConcatenate.create(leftIla,
                                                              rightIla);
        final Object epsilon = Object.class;
        ObjectIlaCheck.checkAll(targetIla, actualIla,
                                IlaTestDimensions.defaultOffsetLength(),
                                IlaTestDimensions.defaultMaxStride(),
                                epsilon);
    }
}
// AUTO GENERATED FROM TEMPLATE
