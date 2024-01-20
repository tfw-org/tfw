package tfw.immutable.ila.booleanila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class BooleanIlaReverseTest {
    @Test
    void testArguments() throws Exception {
        final BooleanIla ila = BooleanIlaFromArray.create(new boolean[10]);
        final boolean[] buffer = new boolean[10];

        assertThrows(IllegalArgumentException.class, () -> BooleanIlaReverse.create(null, buffer));
        assertThrows(IllegalArgumentException.class, () -> BooleanIlaReverse.create(ila, null));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final boolean[] array = new boolean[length];
        final boolean[] reversed = new boolean[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = random.nextBoolean();
        }
        for (int ii = 0; ii < reversed.length; ++ii) {
            reversed[ii] = array[length - 1 - ii];
        }
        BooleanIla origIla = BooleanIlaFromArray.create(array);
        BooleanIla targetIla = BooleanIlaFromArray.create(reversed);
        BooleanIla actualIla = BooleanIlaReverse.create(origIla, new boolean[1000]);
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
