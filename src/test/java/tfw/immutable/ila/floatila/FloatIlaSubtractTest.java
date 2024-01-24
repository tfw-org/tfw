package tfw.immutable.ila.floatila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class FloatIlaSubtractTest {
    @Test
    void testArguments() throws Exception {
        final FloatIla ila1 = FloatIlaFromArray.create(new float[10]);
        final FloatIla ila2 = FloatIlaFromArray.create(new float[20]);

        assertThrows(IllegalArgumentException.class, () -> FloatIlaSubtract.create(null, ila1, 1));
        assertThrows(IllegalArgumentException.class, () -> FloatIlaSubtract.create(ila1, null, 1));
        assertThrows(IllegalArgumentException.class, () -> FloatIlaSubtract.create(ila1, ila2, 1));
        assertThrows(IllegalArgumentException.class, () -> FloatIlaSubtract.create(ila1, ila1, 0));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final float[] leftArray = new float[length];
        final float[] rightArray = new float[length];
        final float[] array = new float[length];
        for (int ii = 0; ii < leftArray.length; ++ii) {
            leftArray[ii] = random.nextFloat();
            rightArray[ii] = random.nextFloat();
            array[ii] = leftArray[ii] - rightArray[ii];
        }
        FloatIla leftIla = FloatIlaFromArray.create(leftArray);
        FloatIla rightIla = FloatIlaFromArray.create(rightArray);
        FloatIla targetIla = FloatIlaFromArray.create(array);
        FloatIla actualIla = FloatIlaSubtract.create(leftIla, rightIla, 100);

        FloatIlaCheck.check(targetIla, actualIla);
    }
}
// AUTO GENERATED FROM TEMPLATE
