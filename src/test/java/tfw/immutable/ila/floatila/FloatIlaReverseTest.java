package tfw.immutable.ila.floatila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class FloatIlaReverseTest {
    @Test
    void testArguments() throws Exception {
        final FloatIla ila = FloatIlaFromArray.create(new float[10]);
        final float[] buffer = new float[10];

        assertThrows(IllegalArgumentException.class, () -> FloatIlaReverse.create(null, buffer));
        assertThrows(IllegalArgumentException.class, () -> FloatIlaReverse.create(ila, null));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final float[] array = new float[length];
        final float[] reversed = new float[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = random.nextFloat();
        }
        for (int ii = 0; ii < reversed.length; ++ii) {
            reversed[ii] = array[length - 1 - ii];
        }
        FloatIla origIla = FloatIlaFromArray.create(array);
        FloatIla targetIla = FloatIlaFromArray.create(reversed);
        FloatIla actualIla = FloatIlaReverse.create(origIla, new float[1000]);

        FloatIlaCheck.check(targetIla, actualIla);
    }
}
// AUTO GENERATED FROM TEMPLATE
