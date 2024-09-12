package tfw.immutable.ila.doubleila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class DoubleIlaInsertTest {
    @Test
    void testArguments() throws Exception {
        final Random random = new Random(0);
        final DoubleIla ila = DoubleIlaFromArray.create(new double[10]);
        final long ilaLength = ila.length();
        final double value = random.nextDouble();

        assertThrows(IllegalArgumentException.class, () -> DoubleIlaInsert.create(null, 0, value));
        assertThrows(IllegalArgumentException.class, () -> DoubleIlaInsert.create(ila, -1, value));
        assertThrows(IllegalArgumentException.class, () -> DoubleIlaInsert.create(ila, ilaLength + 1, value));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final double[] array = new double[length];
        final double[] target = new double[length + 1];
        for (int index = 0; index < length; ++index) {
            final double value = random.nextDouble();
            int skipit = 0;
            for (int ii = 0; ii < array.length; ++ii) {
                if (index == ii) {
                    skipit = 1;
                    target[ii] = value;
                }
                target[ii + skipit] = array[ii] = random.nextDouble();
            }
            DoubleIla origIla = DoubleIlaFromArray.create(array);
            DoubleIla targetIla = DoubleIlaFromArray.create(target);
            DoubleIla actualIla = DoubleIlaInsert.create(origIla, index, value);

            DoubleIlaCheck.check(targetIla, actualIla);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
