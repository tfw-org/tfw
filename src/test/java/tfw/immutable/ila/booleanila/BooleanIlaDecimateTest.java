package tfw.immutable.ila.booleanila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class BooleanIlaDecimateTest {
    @Test
    void testArguments() {
        final BooleanIla ila = BooleanIlaFromArray.create(new boolean[10]);
        final boolean[] buffer = new boolean[10];

        assertThrows(IllegalArgumentException.class, () -> BooleanIlaDecimate.create(null, 2, buffer));
        assertThrows(IllegalArgumentException.class, () -> BooleanIlaDecimate.create(ila, 2, null));
        assertThrows(IllegalArgumentException.class, () -> BooleanIlaDecimate.create(ila, 1, buffer));
        assertThrows(IllegalArgumentException.class, () -> BooleanIlaDecimate.create(ila, 2, new boolean[0]));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final boolean[] array = new boolean[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = random.nextBoolean();
        }
        BooleanIla ila = BooleanIlaFromArray.create(array);
        for (int factor = 2; factor <= length; ++factor) {
            final int targetLength = (length + factor - 1) / factor;
            final boolean[] target = new boolean[targetLength];
            for (int ii = 0; ii < target.length; ++ii) {
                target[ii] = array[ii * factor];
            }
            BooleanIla targetIla = BooleanIlaFromArray.create(target);
            BooleanIla actualIla = BooleanIlaDecimate.create(ila, factor, new boolean[100]);
            final boolean epsilon = false;
            BooleanIlaCheck.checkAll(
                    targetIla,
                    actualIla,
                    IlaTestDimensions.defaultOffsetLength(),
                    IlaTestDimensions.defaultMaxStride(),
                    epsilon);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
