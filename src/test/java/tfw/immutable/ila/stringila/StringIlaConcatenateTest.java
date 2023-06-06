package tfw.immutable.ila.stringila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;

/**
 *
 * @immutables.types=all
 */
public class StringIlaConcatenateTest extends TestCase {
    public void testAll() throws Exception {
        final Random random = new Random(0);
        final int leftLength = IlaTestDimensions.defaultIlaLength();
        final int rightLength = 1 + random.nextInt(leftLength);
        final String[] leftArray = new String[leftLength];
        final String[] rightArray = new String[rightLength];
        final String[] array = new String[leftLength + rightLength];
        for (int ii = 0; ii < leftArray.length; ++ii) {
            array[ii] = leftArray[ii] = new String();
        }
        for (int ii = 0; ii < rightArray.length; ++ii) {
            array[ii + leftLength] = rightArray[ii] = new String();
        }
        StringIla leftIla = StringIlaFromArray.create(leftArray);
        StringIla rightIla = StringIlaFromArray.create(rightArray);
        StringIla targetIla = StringIlaFromArray.create(array);
        StringIla actualIla = StringIlaConcatenate.create(leftIla, rightIla);
        final String epsilon = "";
        StringIlaCheck.checkAll(
                targetIla,
                actualIla,
                IlaTestDimensions.defaultOffsetLength(),
                IlaTestDimensions.defaultMaxStride(),
                epsilon);
    }
}
// AUTO GENERATED FROM TEMPLATE
