package tfw.immutable.ila.floatila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class FloatIlaDecimateTest {
    @Test
    void testArguments() {
        final FloatIla ila = FloatIlaFromArray.create(new float[10]);
        final float[] buffer = new float[10];

        assertThrows(IllegalArgumentException.class, () -> FloatIlaDecimate.create(null, 2, buffer));
        assertThrows(IllegalArgumentException.class, () -> FloatIlaDecimate.create(ila, 2, null));
        assertThrows(IllegalArgumentException.class, () -> FloatIlaDecimate.create(ila, 1, buffer));
        assertThrows(IllegalArgumentException.class, () -> FloatIlaDecimate.create(ila, 2, new float[0]));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final float[] array = new float[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = random.nextFloat();
        }
        FloatIla ila = FloatIlaFromArray.create(array);
        for (int factor = 2; factor <= length; ++factor) {
            final int targetLength = (length + factor - 1) / factor;
            final float[] target = new float[targetLength];
            for (int ii = 0; ii < target.length; ++ii) {
                target[ii] = array[ii * factor];
            }
            FloatIla targetIla = FloatIlaFromArray.create(target);
            FloatIla actualIla = FloatIlaDecimate.create(ila, factor, new float[100]);
            final float epsilon = 0.0f;
            FloatIlaCheck.checkAll(
                    targetIla,
                    actualIla,
                    IlaTestDimensions.defaultOffsetLength(),
                    IlaTestDimensions.defaultMaxStride(),
                    epsilon);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
