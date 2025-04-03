package tfw.immutable.ila.floatila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class FloatIlaReverseTest {
    @Test
    void argumentsTest() {
        final FloatIla ila = FloatIlaFromArray.create(new float[10]);
        final float[] buffer = new float[10];

        assertThatThrownBy(() -> FloatIlaReverse.create(null, buffer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ila == null not allowed!");
        assertThatThrownBy(() -> FloatIlaReverse.create(ila, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("buffer == null not allowed!");
    }

    @Test
    void allTest() throws Exception {
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
