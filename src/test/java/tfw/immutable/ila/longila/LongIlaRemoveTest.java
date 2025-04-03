package tfw.immutable.ila.longila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class LongIlaRemoveTest {
    @Test
    void argumentsTest() throws Exception {
        final LongIla ila = LongIlaFromArray.create(new long[10]);
        final long ilaLength = ila.length();

        assertThatThrownBy(() -> LongIlaRemove.create(null, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ila == null not allowed!");
        assertThatThrownBy(() -> LongIlaRemove.create(ila, -1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("index (=-1) < 0 not allowed!");
        assertThatThrownBy(() -> LongIlaRemove.create(ila, ilaLength))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("index (=10) >= ila.length() (=10) not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final long[] array = new long[length];
        final long[] target = new long[length - 1];
        for (int index = 0; index < length; ++index) {
            int targetii = 0;
            for (int ii = 0; ii < array.length; ++ii) {
                array[ii] = random.nextLong();
                if (ii != index) {
                    target[targetii++] = array[ii];
                }
            }
            LongIla origIla = LongIlaFromArray.create(array);
            LongIla targetIla = LongIlaFromArray.create(target);
            LongIla actualIla = LongIlaRemove.create(origIla, index);

            LongIlaCheck.check(targetIla, actualIla);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
