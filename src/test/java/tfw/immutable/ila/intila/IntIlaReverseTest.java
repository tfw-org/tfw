package tfw.immutable.ila.intila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class IntIlaReverseTest {
    @Test
    void testArguments() {
        final IntIla ila = IntIlaFromArray.create(new int[10]);
        final int[] buffer = new int[10];

        assertThrows(IllegalArgumentException.class, () -> IntIlaReverse.create(null, buffer));
        assertThrows(IllegalArgumentException.class, () -> IntIlaReverse.create(ila, null));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final int[] array = new int[length];
        final int[] reversed = new int[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = random.nextInt();
        }
        for (int ii = 0; ii < reversed.length; ++ii) {
            reversed[ii] = array[length - 1 - ii];
        }
        IntIla origIla = IntIlaFromArray.create(array);
        IntIla targetIla = IntIlaFromArray.create(reversed);
        IntIla actualIla = IntIlaReverse.create(origIla, new int[1000]);

        IntIlaCheck.check(targetIla, actualIla);
    }
}
// AUTO GENERATED FROM TEMPLATE
