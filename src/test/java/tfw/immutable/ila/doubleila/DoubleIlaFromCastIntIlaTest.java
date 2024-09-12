package tfw.immutable.ila.doubleila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.intila.IntIla;
import tfw.immutable.ila.intila.IntIlaFromArray;

class DoubleIlaFromCastIntIlaTest {
    @Test
    void testArguments() {
        final IntIla ila = IntIlaFromArray.create(new int[10]);

        assertThrows(IllegalArgumentException.class, () -> DoubleIlaFromCastIntIla.create(null, 1));
        assertThrows(IllegalArgumentException.class, () -> DoubleIlaFromCastIntIla.create(ila, 0));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final int[] array = new int[length];
        final double[] target = new double[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = random.nextInt();
            target[ii] = (double) array[ii];
        }
        IntIla ila = IntIlaFromArray.create(array);
        DoubleIla targetIla = DoubleIlaFromArray.create(target);
        DoubleIla actualIla = DoubleIlaFromCastIntIla.create(ila, 100);

        DoubleIlaCheck.check(targetIla, actualIla);
    }
}
// AUTO GENERATED FROM TEMPLATE
