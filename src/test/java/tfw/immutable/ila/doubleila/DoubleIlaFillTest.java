package tfw.immutable.ila.doubleila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class DoubleIlaFillTest {
    @Test
    void testArguments() {
        final Random random = new Random(0);
        final double value = random.nextDouble();

        assertThrows(IllegalArgumentException.class, () -> DoubleIlaFill.create(value, -1));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final double value = random.nextDouble();
        final int length = IlaTestDimensions.defaultIlaLength();
        final double[] array = new double[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = value;
        }
        DoubleIla targetIla = DoubleIlaFromArray.create(array);
        DoubleIla actualIla = DoubleIlaFill.create(value, length);

        DoubleIlaCheck.check(targetIla, actualIla);
    }
}
// AUTO GENERATED FROM TEMPLATE
