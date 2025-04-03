package tfw.immutable.ila.floatila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class FloatIlaNegateTest {
    @Test
    void argumentsTest() {
        assertThatThrownBy(() -> FloatIlaNegate.create(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ila == null not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final float[] array = new float[length];
        final float[] target = new float[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = random.nextFloat();
            target[ii] = -array[ii];
        }
        FloatIla ila = FloatIlaFromArray.create(array);
        FloatIla targetIla = FloatIlaFromArray.create(target);
        FloatIla actualIla = FloatIlaNegate.create(ila);

        FloatIlaCheck.check(targetIla, actualIla);
    }
}
// AUTO GENERATED FROM TEMPLATE
