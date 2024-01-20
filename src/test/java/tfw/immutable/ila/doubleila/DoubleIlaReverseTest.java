package tfw.immutable.ila.doubleila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class DoubleIlaReverseTest {
    @Test
    void testArguments() throws Exception {
        final DoubleIla ila = DoubleIlaFromArray.create(new double[10]);
        final double[] buffer = new double[10];

        assertThrows(IllegalArgumentException.class, () -> DoubleIlaReverse.create(null, buffer));
        assertThrows(IllegalArgumentException.class, () -> DoubleIlaReverse.create(ila, null));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final double[] array = new double[length];
        final double[] reversed = new double[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = random.nextDouble();
        }
        for (int ii = 0; ii < reversed.length; ++ii) {
            reversed[ii] = array[length - 1 - ii];
        }
        DoubleIla origIla = DoubleIlaFromArray.create(array);
        DoubleIla targetIla = DoubleIlaFromArray.create(reversed);
        DoubleIla actualIla = DoubleIlaReverse.create(origIla, new double[1000]);
        final double epsilon = 0.0;
        DoubleIlaCheck.checkAll(
                targetIla,
                actualIla,
                IlaTestDimensions.defaultOffsetLength(),
                IlaTestDimensions.defaultMaxStride(),
                epsilon);
    }
}
// AUTO GENERATED FROM TEMPLATE
