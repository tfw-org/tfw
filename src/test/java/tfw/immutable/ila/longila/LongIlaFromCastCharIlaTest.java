package tfw.immutable.ila.longila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.charila.CharIla;
import tfw.immutable.ila.charila.CharIlaFromArray;

final class LongIlaFromCastCharIlaTest {
    @Test
    void argumentsTest() {
        final CharIla ila = CharIlaFromArray.create(new char[10]);

        assertThatThrownBy(() -> LongIlaFromCastCharIla.create(null, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("charIla == null not allowed!");
        assertThatThrownBy(() -> LongIlaFromCastCharIla.create(ila, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("bufferSize (=0) < 1 not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final char[] array = new char[length];
        final long[] target = new long[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = (char) random.nextInt();
            target[ii] = (long) array[ii];
        }
        CharIla ila = CharIlaFromArray.create(array);
        LongIla targetIla = LongIlaFromArray.create(target);
        LongIla actualIla = LongIlaFromCastCharIla.create(ila, 100);

        LongIlaCheck.check(targetIla, actualIla);
    }
}
// AUTO GENERATED FROM TEMPLATE
