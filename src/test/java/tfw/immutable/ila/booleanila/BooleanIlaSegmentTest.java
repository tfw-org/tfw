package tfw.immutable.ila.booleanila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class BooleanIlaSegmentTest {
    @Test
    void testArguments() throws Exception {
        final BooleanIla ila = BooleanIlaFromArray.create(new boolean[10]);
        final long ilaLength = ila.length();

        assertThrows(IllegalArgumentException.class, () -> BooleanIlaSegment.create(null, 0));
        assertThrows(IllegalArgumentException.class, () -> BooleanIlaSegment.create(ila, -1));
        assertThrows(IllegalArgumentException.class, () -> BooleanIlaSegment.create(ila, ilaLength + 1));
        assertThrows(IllegalArgumentException.class, () -> BooleanIlaSegment.create(null, 0, 0));
        assertThrows(IllegalArgumentException.class, () -> BooleanIlaSegment.create(ila, -1, 0));
        assertThrows(IllegalArgumentException.class, () -> BooleanIlaSegment.create(ila, 0, -1));
        assertThrows(IllegalArgumentException.class, () -> BooleanIlaSegment.create(ila, ilaLength + 1, 0));
        assertThrows(IllegalArgumentException.class, () -> BooleanIlaSegment.create(ila, 0, ilaLength + 1));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final boolean[] master = new boolean[length];
        for (int ii = 0; ii < master.length; ++ii) {
            master[ii] = random.nextBoolean();
        }
        BooleanIla masterIla = BooleanIlaFromArray.create(master);
        BooleanIla checkIla = BooleanIlaSegment.create(masterIla, 0, masterIla.length());
        final int offsetLength = IlaTestDimensions.defaultOffsetLength();
        final int maxStride = IlaTestDimensions.defaultMaxStride();
        final boolean epsilon = false;
        BooleanIlaCheck.checkWithoutCorrectness(checkIla, offsetLength, epsilon);
        for (long start = 0; start < length; ++start) {
            for (long len = 0; len < length - start; ++len) {
                boolean[] array = new boolean[(int) len];
                for (int ii = 0; ii < array.length; ++ii) {
                    array[ii] = master[ii + (int) start];
                }
                BooleanIla targetIla = BooleanIlaFromArray.create(array);
                BooleanIla actualIla = BooleanIlaSegment.create(masterIla, start, len);
                BooleanIlaCheck.checkCorrectness(targetIla, actualIla, offsetLength, maxStride, epsilon);
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
