package tfw.immutable.ila.longila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class LongIlaSegmentTest {
    @Test
    void testArguments() throws Exception {
        final LongIla ila = LongIlaFromArray.create(new long[10]);
        final long ilaLength = ila.length();

        assertThrows(IllegalArgumentException.class, () -> LongIlaSegment.create(null, 0));
        assertThrows(IllegalArgumentException.class, () -> LongIlaSegment.create(ila, -1));
        assertThrows(IllegalArgumentException.class, () -> LongIlaSegment.create(ila, ilaLength + 1));
        assertThrows(IllegalArgumentException.class, () -> LongIlaSegment.create(null, 0, 0));
        assertThrows(IllegalArgumentException.class, () -> LongIlaSegment.create(ila, -1, 0));
        assertThrows(IllegalArgumentException.class, () -> LongIlaSegment.create(ila, 0, -1));
        assertThrows(IllegalArgumentException.class, () -> LongIlaSegment.create(ila, ilaLength + 1, 0));
        assertThrows(IllegalArgumentException.class, () -> LongIlaSegment.create(ila, 0, ilaLength + 1));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final long[] master = new long[length];
        for (int ii = 0; ii < master.length; ++ii) {
            master[ii] = random.nextLong();
        }
        LongIla masterIla = LongIlaFromArray.create(master);
        LongIla checkIla = LongIlaSegment.create(masterIla, 0, masterIla.length());
        final int offsetLength = IlaTestDimensions.defaultOffsetLength();
        final int maxStride = IlaTestDimensions.defaultMaxStride();
        final long epsilon = 0L;
        LongIlaCheck.checkWithoutCorrectness(checkIla, offsetLength, epsilon);
        for (long start = 0; start < length; ++start) {
            for (long len = 0; len < length - start; ++len) {
                long[] array = new long[(int) len];
                for (int ii = 0; ii < array.length; ++ii) {
                    array[ii] = master[ii + (int) start];
                }
                LongIla targetIla = LongIlaFromArray.create(array);
                LongIla actualIla = LongIlaSegment.create(masterIla, start, len);
                LongIlaCheck.checkCorrectness(targetIla, actualIla, offsetLength, maxStride, epsilon);
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
