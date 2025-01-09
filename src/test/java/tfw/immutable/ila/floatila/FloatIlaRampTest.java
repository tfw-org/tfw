package tfw.immutable.ila.floatila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class FloatIlaRampTest {
    @Test
    void argumentsTest() {
        final Random random = new Random(0);
        final float start = random.nextFloat();
        final float increment = random.nextFloat();

        assertThatThrownBy(() -> FloatIlaRamp.create(start, increment, -1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("length (=-1) < 0 not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final float startValue = random.nextFloat();
        final float increment = random.nextFloat();
        final int length = IlaTestDimensions.defaultIlaLength();
        final float[] array = new float[length];
        float value = startValue;
        for (int ii = 0; ii < array.length; ++ii, value += increment) {
            array[ii] = value;
        }
        FloatIla targetIla = FloatIlaFromArray.create(array);
        FloatIla actualIla = FloatIlaRamp.create(startValue, increment, length);
        final float epsilon = (float) 0.000001;
        FloatIlaCheck.checkAll(
                targetIla,
                actualIla,
                IlaTestDimensions.defaultOffsetLength(),
                IlaTestDimensions.defaultMaxStride(),
                epsilon);
    }
}
// AUTO GENERATED FROM TEMPLATE
