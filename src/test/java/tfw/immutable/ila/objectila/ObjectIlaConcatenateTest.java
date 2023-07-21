package tfw.immutable.ila.objectila;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

/**
 *
 * @immutables.types=all
 */
class ObjectIlaConcatenateTest {
    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int leftLength = IlaTestDimensions.defaultIlaLength();
        final int rightLength = 1 + random.nextInt(leftLength);
        final Object[] leftArray = new Object[leftLength];
        final Object[] rightArray = new Object[rightLength];
        final Object[] array = new Object[leftLength + rightLength];
        for (int ii = 0; ii < leftArray.length; ++ii) {
            array[ii] = leftArray[ii] = new Object();
        }
        for (int ii = 0; ii < rightArray.length; ++ii) {
            array[ii + leftLength] = rightArray[ii] = new Object();
        }
        ObjectIla<Object> leftIla = ObjectIlaFromArray.create(leftArray);
        ObjectIla<Object> rightIla = ObjectIlaFromArray.create(rightArray);
        ObjectIla<Object> targetIla = ObjectIlaFromArray.create(array);
        ObjectIla<Object> actualIla = ObjectIlaConcatenate.create(leftIla, rightIla);
        final Object epsilon = Object.class;
        ObjectIlaCheck.checkAll(
                targetIla,
                actualIla,
                IlaTestDimensions.defaultOffsetLength(),
                IlaTestDimensions.defaultMaxStride(),
                epsilon);
    }
}
// AUTO GENERATED FROM TEMPLATE
