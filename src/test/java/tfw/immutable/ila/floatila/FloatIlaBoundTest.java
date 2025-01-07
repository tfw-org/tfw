package tfw.immutable.ila.floatila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class FloatIlaBoundTest {
    @Test
    void argumentsTest() {
        final float low = 5;
        final float high = 10;
        final FloatIla ila = FloatIlaFromArray.create(new float[10]);

        assertThatThrownBy(() -> FloatIlaBound.create(null, low, high))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ila == null not allowed!");
        assertThatThrownBy(() -> FloatIlaBound.create(ila, high, low))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("minimum (=" + high + ") > maximum (=" + low + ") not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final float[] array = new float[length];
        final float[] target = new float[length];
        float minimum = random.nextFloat();
        float maximum = random.nextFloat();
        if (minimum > maximum) {
            float tmp = minimum;
            minimum = maximum;
            maximum = tmp;
        }
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = random.nextFloat();
            target[ii] = array[ii];
            if (target[ii] < minimum) {
                target[ii] = minimum;
            } else if (target[ii] > maximum) {
                target[ii] = maximum;
            }
        }
        FloatIla ila = FloatIlaFromArray.create(array);
        FloatIla targetIla = FloatIlaFromArray.create(target);
        FloatIla actualIla = FloatIlaBound.create(ila, minimum, maximum);

        FloatIlaCheck.check(targetIla, actualIla);
    }
}
// AUTO GENERATED FROM TEMPLATE
