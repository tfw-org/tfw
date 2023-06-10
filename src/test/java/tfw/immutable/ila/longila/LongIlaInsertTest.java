package tfw.immutable.ila.longila;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

/**
 *
 * @immutables.types=all
 */
class LongIlaInsertTest {
    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final long[] array = new long[length];
        final long[] target = new long[length + 1];
        for (int index = 0; index < length; ++index) {
            final long value = random.nextLong();
            int skipit = 0;
            for (int ii = 0; ii < array.length; ++ii) {
                if (index == ii) {
                    skipit = 1;
                    target[ii] = value;
                }
                target[ii + skipit] = array[ii] = random.nextLong();
            }
            LongIla origIla = LongIlaFromArray.create(array);
            LongIla targetIla = LongIlaFromArray.create(target);
            LongIla actualIla = LongIlaInsert.create(origIla, index, value);
            final long epsilon = 0L;
            LongIlaCheck.checkAll(
                    targetIla,
                    actualIla,
                    IlaTestDimensions.defaultOffsetLength(),
                    IlaTestDimensions.defaultMaxStride(),
                    epsilon);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
