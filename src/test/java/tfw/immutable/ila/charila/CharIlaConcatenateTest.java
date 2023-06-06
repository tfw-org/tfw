package tfw.immutable.ila.charila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;

/**
 *
 * @immutables.types=all
 */
public class CharIlaConcatenateTest extends TestCase {
    public void testAll() throws Exception {
        final Random random = new Random(0);
        final int leftLength = IlaTestDimensions.defaultIlaLength();
        final int rightLength = 1 + random.nextInt(leftLength);
        final char[] leftArray = new char[leftLength];
        final char[] rightArray = new char[rightLength];
        final char[] array = new char[leftLength + rightLength];
        for (int ii = 0; ii < leftArray.length; ++ii) {
            array[ii] = leftArray[ii] = (char) random.nextInt();
        }
        for (int ii = 0; ii < rightArray.length; ++ii) {
            array[ii + leftLength] = rightArray[ii] = (char) random.nextInt();
        }
        CharIla leftIla = CharIlaFromArray.create(leftArray);
        CharIla rightIla = CharIlaFromArray.create(rightArray);
        CharIla targetIla = CharIlaFromArray.create(array);
        CharIla actualIla = CharIlaConcatenate.create(leftIla, rightIla);
        final char epsilon = (char) 0;
        CharIlaCheck.checkAll(
                targetIla,
                actualIla,
                IlaTestDimensions.defaultOffsetLength(),
                IlaTestDimensions.defaultMaxStride(),
                epsilon);
    }
}
// AUTO GENERATED FROM TEMPLATE
