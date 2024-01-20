package tfw.immutable.ila.longila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class LongIlaRemoveTest {
    @Test
    void testArguments() throws Exception {
        final LongIla ila = LongIlaFromArray.create(new long[10]);
        final long ilaLength = ila.length();

        assertThrows(IllegalArgumentException.class, () -> LongIlaRemove.create(null, 0));
        assertThrows(IllegalArgumentException.class, () -> LongIlaRemove.create(ila, -1));
        assertThrows(IllegalArgumentException.class, () -> LongIlaRemove.create(ila, ilaLength));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final long[] array = new long[length];
        final long[] target = new long[length - 1];
        for (int index = 0; index < length; ++index) {
            int targetii = 0;
            for (int ii = 0; ii < array.length; ++ii) {
                array[ii] = random.nextLong();
                if (ii != index) {
                    target[targetii++] = array[ii];
                }
            }
            LongIla origIla = LongIlaFromArray.create(array);
            LongIla targetIla = LongIlaFromArray.create(target);
            LongIla actualIla = LongIlaRemove.create(origIla, index);
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
