package tfw.immutable.ila.longila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;

/**
 *
 * @immutables.types=all
 */
public class LongIlaConcatenateTest extends TestCase {
    public void testAll() throws Exception {
        final Random random = new Random(0);
        final int leftLength = IlaTestDimensions.defaultIlaLength();
        final int rightLength = 1 + random.nextInt(leftLength);
        final long[] leftArray = new long[leftLength];
        final long[] rightArray = new long[rightLength];
        final long[] array = new long[leftLength + rightLength];
        for (int ii = 0; ii < leftArray.length; ++ii) {
            array[ii] = leftArray[ii] = random.nextLong();
        }
        for (int ii = 0; ii < rightArray.length; ++ii) {
            array[ii + leftLength] = rightArray[ii] = random.nextLong();
        }
        LongIla leftIla = LongIlaFromArray.create(leftArray);
        LongIla rightIla = LongIlaFromArray.create(rightArray);
        LongIla targetIla = LongIlaFromArray.create(array);
        LongIla actualIla = LongIlaConcatenate.create(leftIla, rightIla);
        final long epsilon = 0L;
        LongIlaCheck.checkAll(
                targetIla,
                actualIla,
                IlaTestDimensions.defaultOffsetLength(),
                IlaTestDimensions.defaultMaxStride(),
                epsilon);
    }
}
// AUTO GENERATED FROM TEMPLATE
