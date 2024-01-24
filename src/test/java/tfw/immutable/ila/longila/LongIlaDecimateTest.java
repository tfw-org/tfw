package tfw.immutable.ila.longila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class LongIlaDecimateTest {
    @Test
    void testArguments() {
        final LongIla ila = LongIlaFromArray.create(new long[10]);
        final long[] buffer = new long[10];

        assertThrows(IllegalArgumentException.class, () -> LongIlaDecimate.create(null, 2, buffer));
        assertThrows(IllegalArgumentException.class, () -> LongIlaDecimate.create(ila, 2, null));
        assertThrows(IllegalArgumentException.class, () -> LongIlaDecimate.create(ila, 1, buffer));
        assertThrows(IllegalArgumentException.class, () -> LongIlaDecimate.create(ila, 2, new long[0]));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final long[] array = new long[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = random.nextLong();
        }
        LongIla ila = LongIlaFromArray.create(array);
        for (int factor = 2; factor <= length; ++factor) {
            final int targetLength = (length + factor - 1) / factor;
            final long[] target = new long[targetLength];
            for (int ii = 0; ii < target.length; ++ii) {
                target[ii] = array[ii * factor];
            }
            LongIla targetIla = LongIlaFromArray.create(target);
            LongIla actualIla = LongIlaDecimate.create(ila, factor, new long[100]);

            LongIlaCheck.check(targetIla, actualIla);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
