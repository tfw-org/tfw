package tfw.immutable.ila.booleanila;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

/**
 *
 * @immutables.types=all
 */
class BooleanIlaConcatenateTest {
    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int leftLength = IlaTestDimensions.defaultIlaLength();
        final int rightLength = 1 + random.nextInt(leftLength);
        final boolean[] leftArray = new boolean[leftLength];
        final boolean[] rightArray = new boolean[rightLength];
        final boolean[] array = new boolean[leftLength + rightLength];
        for (int ii = 0; ii < leftArray.length; ++ii) {
            array[ii] = leftArray[ii] = random.nextBoolean();
        }
        for (int ii = 0; ii < rightArray.length; ++ii) {
            array[ii + leftLength] = rightArray[ii] = random.nextBoolean();
        }
        BooleanIla leftIla = BooleanIlaFromArray.create(leftArray);
        BooleanIla rightIla = BooleanIlaFromArray.create(rightArray);
        BooleanIla targetIla = BooleanIlaFromArray.create(array);
        BooleanIla actualIla = BooleanIlaConcatenate.create(leftIla, rightIla);
        final boolean epsilon = false;
        BooleanIlaCheck.checkAll(
                targetIla,
                actualIla,
                IlaTestDimensions.defaultOffsetLength(),
                IlaTestDimensions.defaultMaxStride(),
                epsilon);
    }
}
// AUTO GENERATED FROM TEMPLATE
