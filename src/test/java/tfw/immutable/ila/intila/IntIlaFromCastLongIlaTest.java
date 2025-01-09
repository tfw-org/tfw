package tfw.immutable.ila.intila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaFromArray;

final class IntIlaFromCastLongIlaTest {
    @Test
    void argumentsTest() {
        final LongIla ila = LongIlaFromArray.create(new long[10]);

        assertThatThrownBy(() -> IntIlaFromCastLongIla.create(null, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("longIla == null not allowed!");
        assertThatThrownBy(() -> IntIlaFromCastLongIla.create(ila, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("bufferSize (=0) < 1 not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final long[] array = new long[length];
        final int[] target = new int[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = random.nextLong();
            target[ii] = (int) array[ii];
        }
        LongIla ila = LongIlaFromArray.create(array);
        IntIla targetIla = IntIlaFromArray.create(target);
        IntIla actualIla = IntIlaFromCastLongIla.create(ila, 100);

        IntIlaCheck.check(targetIla, actualIla);
    }
}
// AUTO GENERATED FROM TEMPLATE
