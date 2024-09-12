package tfw.immutable.ila.intila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class IntIlaDecimateTest {
    @Test
    void testArguments() {
        final IntIla ila = IntIlaFromArray.create(new int[10]);
        final int[] buffer = new int[10];

        assertThrows(IllegalArgumentException.class, () -> IntIlaDecimate.create(null, 2, buffer));
        assertThrows(IllegalArgumentException.class, () -> IntIlaDecimate.create(ila, 2, null));
        assertThrows(IllegalArgumentException.class, () -> IntIlaDecimate.create(ila, 1, buffer));
        assertThrows(IllegalArgumentException.class, () -> IntIlaDecimate.create(ila, 2, new int[0]));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final int[] array = new int[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = random.nextInt();
        }
        IntIla ila = IntIlaFromArray.create(array);
        for (int factor = 2; factor <= length; ++factor) {
            final int targetLength = (length + factor - 1) / factor;
            final int[] target = new int[targetLength];
            for (int ii = 0; ii < target.length; ++ii) {
                target[ii] = array[ii * factor];
            }
            IntIla targetIla = IntIlaFromArray.create(target);
            IntIla actualIla = IntIlaDecimate.create(ila, factor, new int[100]);

            IntIlaCheck.check(targetIla, actualIla);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
