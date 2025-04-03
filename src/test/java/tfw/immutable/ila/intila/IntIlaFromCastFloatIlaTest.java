package tfw.immutable.ila.intila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.floatila.FloatIla;
import tfw.immutable.ila.floatila.FloatIlaFromArray;

final class IntIlaFromCastFloatIlaTest {
    @Test
    void argumentsTest() {
        final FloatIla ila = FloatIlaFromArray.create(new float[10]);

        assertThatThrownBy(() -> IntIlaFromCastFloatIla.create(null, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("floatIla == null not allowed!");
        assertThatThrownBy(() -> IntIlaFromCastFloatIla.create(ila, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("bufferSize (=0) < 1 not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final float[] array = new float[length];
        final int[] target = new int[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = random.nextFloat();
            target[ii] = (int) array[ii];
        }
        FloatIla ila = FloatIlaFromArray.create(array);
        IntIla targetIla = IntIlaFromArray.create(target);
        IntIla actualIla = IntIlaFromCastFloatIla.create(ila, 100);

        IntIlaCheck.check(targetIla, actualIla);
    }
}
// AUTO GENERATED FROM TEMPLATE
