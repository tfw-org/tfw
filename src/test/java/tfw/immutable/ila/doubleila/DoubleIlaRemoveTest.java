package tfw.immutable.ila.doubleila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class DoubleIlaRemoveTest {
    @Test
    void testArguments() throws Exception {
        final DoubleIla ila = DoubleIlaFromArray.create(new double[10]);
        final long ilaLength = ila.length();

        assertThrows(IllegalArgumentException.class, () -> DoubleIlaRemove.create(null, 0));
        assertThrows(IllegalArgumentException.class, () -> DoubleIlaRemove.create(ila, -1));
        assertThrows(IllegalArgumentException.class, () -> DoubleIlaRemove.create(ila, ilaLength));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final double[] array = new double[length];
        final double[] target = new double[length - 1];
        for (int index = 0; index < length; ++index) {
            int targetii = 0;
            for (int ii = 0; ii < array.length; ++ii) {
                array[ii] = random.nextDouble();
                if (ii != index) {
                    target[targetii++] = array[ii];
                }
            }
            DoubleIla origIla = DoubleIlaFromArray.create(array);
            DoubleIla targetIla = DoubleIlaFromArray.create(target);
            DoubleIla actualIla = DoubleIlaRemove.create(origIla, index);

            DoubleIlaCheck.check(targetIla, actualIla);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
