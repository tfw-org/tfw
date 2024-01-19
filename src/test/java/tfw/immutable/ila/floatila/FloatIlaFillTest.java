package tfw.immutable.ila.floatila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class FloatIlaFillTest {
    @Test
    void testArguments() {
        final Random random = new Random(0);
        final float value = random.nextFloat();

        assertThrows(IllegalArgumentException.class, () -> FloatIlaFill.create(value, -1));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final float value = random.nextFloat();
        final int length = IlaTestDimensions.defaultIlaLength();
        final float[] array = new float[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = value;
        }
        FloatIla targetIla = FloatIlaFromArray.create(array);
        FloatIla actualIla = FloatIlaFill.create(value, length);
        final float epsilon = 0.0f;
        FloatIlaCheck.checkAll(
                targetIla,
                actualIla,
                IlaTestDimensions.defaultOffsetLength(),
                IlaTestDimensions.defaultMaxStride(),
                epsilon);
    }
}
// AUTO GENERATED FROM TEMPLATE
