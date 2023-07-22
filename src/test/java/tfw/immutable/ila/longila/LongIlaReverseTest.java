package tfw.immutable.ila.longila;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class LongIlaReverseTest {
    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final long[] array = new long[length];
        final long[] reversed = new long[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = random.nextLong();
        }
        for (int ii = 0; ii < reversed.length; ++ii) {
            reversed[ii] = array[length - 1 - ii];
        }
        LongIla origIla = LongIlaFromArray.create(array);
        LongIla targetIla = LongIlaFromArray.create(reversed);
        LongIla actualIla = LongIlaReverse.create(origIla);
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
