package tfw.immutable.ila.booleanila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class BooleanIlaRemoveTest {
    @Test
    void testArguments() throws Exception {
        final BooleanIla ila = BooleanIlaFromArray.create(new boolean[10]);
        final long ilaLength = ila.length();

        assertThrows(IllegalArgumentException.class, () -> BooleanIlaRemove.create(null, 0));
        assertThrows(IllegalArgumentException.class, () -> BooleanIlaRemove.create(ila, -1));
        assertThrows(IllegalArgumentException.class, () -> BooleanIlaRemove.create(ila, ilaLength));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final boolean[] array = new boolean[length];
        final boolean[] target = new boolean[length - 1];
        for (int index = 0; index < length; ++index) {
            int targetii = 0;
            for (int ii = 0; ii < array.length; ++ii) {
                array[ii] = random.nextBoolean();
                if (ii != index) {
                    target[targetii++] = array[ii];
                }
            }
            BooleanIla origIla = BooleanIlaFromArray.create(array);
            BooleanIla targetIla = BooleanIlaFromArray.create(target);
            BooleanIla actualIla = BooleanIlaRemove.create(origIla, index);

            BooleanIlaCheck.check(targetIla, actualIla);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
