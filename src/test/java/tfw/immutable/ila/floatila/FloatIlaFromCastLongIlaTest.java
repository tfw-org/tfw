package tfw.immutable.ila.floatila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaFromArray;

final class FloatIlaFromCastLongIlaTest {
    @Test
    void argumentsTest() {
        final LongIla ila = LongIlaFromArray.create(new long[10]);

        assertThatThrownBy(() -> FloatIlaFromCastLongIla.create(null, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("longIla == null not allowed!");
        assertThatThrownBy(() -> FloatIlaFromCastLongIla.create(ila, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("bufferSize (=0) < 1 not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final long[] array = new long[length];
        final float[] target = new float[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = random.nextLong();
            target[ii] = (float) array[ii];
        }
        LongIla ila = LongIlaFromArray.create(array);
        FloatIla targetIla = FloatIlaFromArray.create(target);
        FloatIla actualIla = FloatIlaFromCastLongIla.create(ila, 100);

        FloatIlaCheck.check(targetIla, actualIla);
    }
}
// AUTO GENERATED FROM TEMPLATE
