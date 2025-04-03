package tfw.immutable.ila.longila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.floatila.FloatIla;
import tfw.immutable.ila.floatila.FloatIlaFromArray;

final class LongIlaFromCastFloatIlaTest {
    @Test
    void argumentsTest() {
        final FloatIla ila = FloatIlaFromArray.create(new float[10]);

        assertThatThrownBy(() -> LongIlaFromCastFloatIla.create(null, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("floatIla == null not allowed!");
        assertThatThrownBy(() -> LongIlaFromCastFloatIla.create(ila, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("bufferSize (=0) < 1 not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final float[] array = new float[length];
        final long[] target = new long[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = random.nextFloat();
            target[ii] = (long) array[ii];
        }
        FloatIla ila = FloatIlaFromArray.create(array);
        LongIla targetIla = LongIlaFromArray.create(target);
        LongIla actualIla = LongIlaFromCastFloatIla.create(ila, 100);

        LongIlaCheck.check(targetIla, actualIla);
    }
}
// AUTO GENERATED FROM TEMPLATE
