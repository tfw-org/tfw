package tfw.immutable.ila.doubleila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class DoubleIlaDecimateTest {
    @Test
    void testArguments() {
        final DoubleIla ila = DoubleIlaFromArray.create(new double[10]);
        final double[] buffer = new double[10];

        assertThrows(IllegalArgumentException.class, () -> DoubleIlaDecimate.create(null, 2, buffer));
        assertThrows(IllegalArgumentException.class, () -> DoubleIlaDecimate.create(ila, 2, null));
        assertThrows(IllegalArgumentException.class, () -> DoubleIlaDecimate.create(ila, 1, buffer));
        assertThrows(IllegalArgumentException.class, () -> DoubleIlaDecimate.create(ila, 2, new double[0]));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final double[] array = new double[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = random.nextDouble();
        }
        DoubleIla ila = DoubleIlaFromArray.create(array);
        for (int factor = 2; factor <= length; ++factor) {
            final int targetLength = (length + factor - 1) / factor;
            final double[] target = new double[targetLength];
            for (int ii = 0; ii < target.length; ++ii) {
                target[ii] = array[ii * factor];
            }
            DoubleIla targetIla = DoubleIlaFromArray.create(target);
            DoubleIla actualIla = DoubleIlaDecimate.create(ila, factor, new double[100]);
            final double epsilon = 0.0;
            DoubleIlaCheck.checkAll(
                    targetIla,
                    actualIla,
                    IlaTestDimensions.defaultOffsetLength(),
                    IlaTestDimensions.defaultMaxStride(),
                    epsilon);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
