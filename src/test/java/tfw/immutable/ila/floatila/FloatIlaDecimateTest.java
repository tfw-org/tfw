package tfw.immutable.ila.floatila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class FloatIlaDecimateTest {
    @Test
    void argumentsTest() {
        final FloatIla ila = FloatIlaFromArray.create(new float[10]);
        final float[] buffer = new float[10];

        assertThatThrownBy(() -> FloatIlaDecimate.create(null, 2, buffer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ila == null not allowed!");
        assertThatThrownBy(() -> FloatIlaDecimate.create(ila, 2, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("buffer == null not allowed!");
        assertThatThrownBy(() -> FloatIlaDecimate.create(ila, 1, buffer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("factor (=1) < 2 not allowed!");
        assertThatThrownBy(() -> FloatIlaDecimate.create(ila, 2, new float[0]))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("buffer.length (=0) < 1 not allowed!");
    }

    @Test
    void allTest() throws Exception {
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

            FloatIlaCheck.check(targetIla, actualIla);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
