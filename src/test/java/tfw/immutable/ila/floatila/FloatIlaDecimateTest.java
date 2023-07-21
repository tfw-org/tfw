package tfw.immutable.ila.floatila;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

/**
 *
 * @immutables.types=all
 */
class FloatIlaDecimateTest {
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
