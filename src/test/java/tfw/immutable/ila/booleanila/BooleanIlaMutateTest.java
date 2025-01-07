package tfw.immutable.ila.booleanila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class BooleanIlaMutateTest {
    @Test
    void argumentsTest() throws Exception {
        final Random random = new Random(0);
        final BooleanIla ila = BooleanIlaFromArray.create(new boolean[10]);
        final long ilaLength = ila.length();
        final boolean value = random.nextBoolean();

        assertThatThrownBy(() -> BooleanIlaMutate.create(null, 0, value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ila == null not allowed!");
        assertThatThrownBy(() -> BooleanIlaMutate.create(ila, -1, value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("index (=-1) < 0 not allowed!");
        assertThatThrownBy(() -> BooleanIlaMutate.create(ila, ilaLength, value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("index (=10) >= ila.length() (=10) not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final boolean[] array = new boolean[length];
        final boolean[] target = new boolean[length];
        for (int index = 0; index < length; ++index) {
            for (int ii = 0; ii < array.length; ++ii) {
                array[ii] = target[ii] = random.nextBoolean();
            }
            final boolean value = random.nextBoolean();
            target[index] = value;
            BooleanIla origIla = BooleanIlaFromArray.create(array);
            BooleanIla targetIla = BooleanIlaFromArray.create(target);
            BooleanIla actualIla = BooleanIlaMutate.create(origIla, index, value);

            BooleanIlaCheck.check(targetIla, actualIla);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
