package tfw.immutable.ila.booleanila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class BooleanIlaFillTest {
    @Test
    void argumentsTest() {
        final Random random = new Random(0);
        final boolean value = random.nextBoolean();

        assertThatThrownBy(() -> BooleanIlaFill.create(value, -1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("length (=-1) < 0 not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final boolean value = random.nextBoolean();
        final int length = IlaTestDimensions.defaultIlaLength();
        final boolean[] array = new boolean[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = value;
        }
        BooleanIla targetIla = BooleanIlaFromArray.create(array);
        BooleanIla actualIla = BooleanIlaFill.create(value, length);

        BooleanIlaCheck.check(targetIla, actualIla);
    }
}
// AUTO GENERATED FROM TEMPLATE
