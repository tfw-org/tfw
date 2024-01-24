package tfw.immutable.ila.intila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class IntIlaRemoveTest {
    @Test
    void testArguments() throws Exception {
        final IntIla ila = IntIlaFromArray.create(new int[10]);
        final long ilaLength = ila.length();

        assertThrows(IllegalArgumentException.class, () -> IntIlaRemove.create(null, 0));
        assertThrows(IllegalArgumentException.class, () -> IntIlaRemove.create(ila, -1));
        assertThrows(IllegalArgumentException.class, () -> IntIlaRemove.create(ila, ilaLength));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final int[] array = new int[length];
        final int[] target = new int[length - 1];
        for (int index = 0; index < length; ++index) {
            int targetii = 0;
            for (int ii = 0; ii < array.length; ++ii) {
                array[ii] = random.nextInt();
                if (ii != index) {
                    target[targetii++] = array[ii];
                }
            }
            IntIla origIla = IntIlaFromArray.create(array);
            IntIla targetIla = IntIlaFromArray.create(target);
            IntIla actualIla = IntIlaRemove.create(origIla, index);

            IntIlaCheck.check(targetIla, actualIla);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
