package tfw.immutable.ila.longila;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class LongIlaRampTest {
    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final long startValue = random.nextLong();
        final long increment = random.nextLong();
        final int length = IlaTestDimensions.defaultIlaLength();
        final long[] array = new long[length];
        long value = startValue;
        for (int ii = 0; ii < array.length; ++ii, value += increment) {
            array[ii] = value;
        }
        LongIla targetIla = LongIlaFromArray.create(array);
        LongIla actualIla = LongIlaRamp.create(startValue, increment, length);
        final long epsilon = (long) 0.000001;
        LongIlaCheck.checkAll(
                targetIla,
                actualIla,
                IlaTestDimensions.defaultOffsetLength(),
                IlaTestDimensions.defaultMaxStride(),
                epsilon);
    }
}
// AUTO GENERATED FROM TEMPLATE
