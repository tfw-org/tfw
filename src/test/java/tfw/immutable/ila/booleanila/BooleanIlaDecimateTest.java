package tfw.immutable.ila.booleanila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class BooleanIlaDecimateTest {
    @Test
    void argumentsTest() {
        final BooleanIla ila = BooleanIlaFromArray.create(new boolean[10]);
        final boolean[] buffer = new boolean[10];

        assertThatThrownBy(() -> BooleanIlaDecimate.create(null, 2, buffer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ila == null not allowed!");
        assertThatThrownBy(() -> BooleanIlaDecimate.create(ila, 2, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("buffer == null not allowed!");
        assertThatThrownBy(() -> BooleanIlaDecimate.create(ila, 1, buffer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("factor (=1) < 2 not allowed!");
        assertThatThrownBy(() -> BooleanIlaDecimate.create(ila, 2, new boolean[0]))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("buffer.length (=0) < 1 not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final boolean[] array = new boolean[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = random.nextBoolean();
        }
        BooleanIla ila = BooleanIlaFromArray.create(array);
        for (int factor = 2; factor <= length; ++factor) {
            final int targetLength = (length + factor - 1) / factor;
            final boolean[] target = new boolean[targetLength];
            for (int ii = 0; ii < target.length; ++ii) {
                target[ii] = array[ii * factor];
            }
            BooleanIla targetIla = BooleanIlaFromArray.create(target);
            BooleanIla actualIla = BooleanIlaDecimate.create(ila, factor, new boolean[100]);

            BooleanIlaCheck.check(targetIla, actualIla);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
