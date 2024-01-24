package tfw.immutable.ila.doubleila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class DoubleIlaMutateTest {
    @Test
    void testArguments() throws Exception {
        final Random random = new Random(0);
        final DoubleIla ila = DoubleIlaFromArray.create(new double[10]);
        final long ilaLength = ila.length();
        final double value = random.nextDouble();

        assertThrows(IllegalArgumentException.class, () -> DoubleIlaMutate.create(null, 0, value));
        assertThrows(IllegalArgumentException.class, () -> DoubleIlaMutate.create(ila, -1, value));
        assertThrows(IllegalArgumentException.class, () -> DoubleIlaMutate.create(ila, ilaLength, value));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final double[] array = new double[length];
        final double[] target = new double[length];
        for (int index = 0; index < length; ++index) {
            for (int ii = 0; ii < array.length; ++ii) {
                array[ii] = target[ii] = random.nextDouble();
            }
            final double value = random.nextDouble();
            target[index] = value;
            DoubleIla origIla = DoubleIlaFromArray.create(array);
            DoubleIla targetIla = DoubleIlaFromArray.create(target);
            DoubleIla actualIla = DoubleIlaMutate.create(origIla, index, value);

            DoubleIlaCheck.check(targetIla, actualIla);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
