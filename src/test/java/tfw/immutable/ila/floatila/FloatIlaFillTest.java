package tfw.immutable.ila.floatila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class FloatIlaFillTest {
    @Test
    void argumentsTest() {
        final Random random = new Random(0);
        final float value = random.nextFloat();

        assertThatThrownBy(() -> FloatIlaFill.create(value, -1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("length (=-1) < 0 not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final float value = random.nextFloat();
        final int length = IlaTestDimensions.defaultIlaLength();
        final float[] array = new float[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = value;
        }
        FloatIla targetIla = FloatIlaFromArray.create(array);
        FloatIla actualIla = FloatIlaFill.create(value, length);

        FloatIlaCheck.check(targetIla, actualIla);
    }
}
// AUTO GENERATED FROM TEMPLATE
