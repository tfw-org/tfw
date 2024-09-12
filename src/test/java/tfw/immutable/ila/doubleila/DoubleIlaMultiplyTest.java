package tfw.immutable.ila.doubleila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class DoubleIlaMultiplyTest {
    @Test
    void testArguments() {
        final DoubleIla ila1 = DoubleIlaFromArray.create(new double[10]);
        final DoubleIla ila2 = DoubleIlaFromArray.create(new double[20]);

        assertThrows(IllegalArgumentException.class, () -> DoubleIlaMultiply.create(null, ila1, 10));
        assertThrows(IllegalArgumentException.class, () -> DoubleIlaMultiply.create(ila1, null, 10));
        assertThrows(IllegalArgumentException.class, () -> DoubleIlaMultiply.create(ila1, ila1, 0));
        assertThrows(IllegalArgumentException.class, () -> DoubleIlaMultiply.create(ila1, ila2, 10));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final double[] leftArray = new double[length];
        final double[] rightArray = new double[length];
        final double[] array = new double[length];
        for (int ii = 0; ii < leftArray.length; ++ii) {
            leftArray[ii] = random.nextDouble();
            rightArray[ii] = random.nextDouble();
            array[ii] = leftArray[ii] * rightArray[ii];
        }
        DoubleIla leftIla = DoubleIlaFromArray.create(leftArray);
        DoubleIla rightIla = DoubleIlaFromArray.create(rightArray);
        DoubleIla targetIla = DoubleIlaFromArray.create(array);
        DoubleIla actualIla = DoubleIlaMultiply.create(leftIla, rightIla, 100);

        DoubleIlaCheck.check(targetIla, actualIla);
    }
}
// AUTO GENERATED FROM TEMPLATE
