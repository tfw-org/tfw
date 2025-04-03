package tfw.immutable.ila.longila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class LongIlaRampTest {
    @Test
    void argumentsTest() {
        final Random random = new Random(0);
        final long start = random.nextLong();
        final long increment = random.nextLong();

        assertThatThrownBy(() -> LongIlaRamp.create(start, increment, -1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("length (=-1) < 0 not allowed!");
    }

    @Test
    void allTest() throws Exception {
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
