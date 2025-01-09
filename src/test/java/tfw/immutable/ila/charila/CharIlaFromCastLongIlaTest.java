package tfw.immutable.ila.charila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaFromArray;

final class CharIlaFromCastLongIlaTest {
    @Test
    void argumentsTest() {
        final LongIla ila = LongIlaFromArray.create(new long[10]);

        assertThatThrownBy(() -> CharIlaFromCastLongIla.create(null, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("longIla == null not allowed!");
        assertThatThrownBy(() -> CharIlaFromCastLongIla.create(ila, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("bufferSize (=0) < 1 not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final long[] array = new long[length];
        final char[] target = new char[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = random.nextLong();
            target[ii] = (char) array[ii];
        }
        LongIla ila = LongIlaFromArray.create(array);
        CharIla targetIla = CharIlaFromArray.create(target);
        CharIla actualIla = CharIlaFromCastLongIla.create(ila, 100);

        CharIlaCheck.check(targetIla, actualIla);
    }
}
// AUTO GENERATED FROM TEMPLATE
