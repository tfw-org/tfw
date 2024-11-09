package tfw.immutable.ila.intila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class IntIlaScalarAddTest {
    @Test
    void testArguments() {
        final Random random = new Random(0);
        final int value = random.nextInt();

        assertThrows(IllegalArgumentException.class, () -> IntIlaScalarAdd.create(null, value));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final int scalar = random.nextInt();
        final int[] array = new int[length];
        final int[] target = new int[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = random.nextInt();
            target[ii] = array[ii] + scalar;
        }
        IntIla ila = IntIlaFromArray.create(array);
        IntIla targetIla = IntIlaFromArray.create(target);
        IntIla actualIla = IntIlaScalarAdd.create(ila, scalar);

        IntIlaCheck.check(targetIla, actualIla);
    }
}
// AUTO GENERATED FROM TEMPLATE
