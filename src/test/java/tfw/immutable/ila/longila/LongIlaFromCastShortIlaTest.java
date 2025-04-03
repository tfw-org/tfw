package tfw.immutable.ila.longila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.shortila.ShortIla;
import tfw.immutable.ila.shortila.ShortIlaFromArray;

final class LongIlaFromCastShortIlaTest {
    @Test
    void argumentsTest() {
        final ShortIla ila = ShortIlaFromArray.create(new short[10]);

        assertThatThrownBy(() -> LongIlaFromCastShortIla.create(null, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("shortIla == null not allowed!");
        assertThatThrownBy(() -> LongIlaFromCastShortIla.create(ila, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("bufferSize (=0) < 1 not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final short[] array = new short[length];
        final long[] target = new long[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = (short) random.nextInt();
            target[ii] = (long) array[ii];
        }
        ShortIla ila = ShortIlaFromArray.create(array);
        LongIla targetIla = LongIlaFromArray.create(target);
        LongIla actualIla = LongIlaFromCastShortIla.create(ila, 100);

        LongIlaCheck.check(targetIla, actualIla);
    }
}
// AUTO GENERATED FROM TEMPLATE
