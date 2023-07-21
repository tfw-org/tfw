package tfw.immutable.ila.longila;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

/**
 *
 * @immutables.types=numeric
 */
class LongIlaDivideTest {
    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final long[] leftArray = new long[length];
        final long[] rightArray = new long[length];
        final long[] array = new long[length];
        for (int ii = 0; ii < leftArray.length; ++ii) {
            leftArray[ii] = random.nextLong();
            rightArray[ii] = random.nextLong();
            array[ii] = (leftArray[ii] / rightArray[ii]);
        }
        LongIla leftIla = LongIlaFromArray.create(leftArray);
        LongIla rightIla = LongIlaFromArray.create(rightArray);
        LongIla targetIla = LongIlaFromArray.create(array);
        LongIla actualIla = LongIlaDivide.create(leftIla, rightIla, 100);
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
