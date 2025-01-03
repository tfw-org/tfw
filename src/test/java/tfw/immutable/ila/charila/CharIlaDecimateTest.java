package tfw.immutable.ila.charila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class CharIlaDecimateTest {
    @Test
    void argumentsTest() {
        final CharIla ila = CharIlaFromArray.create(new char[10]);
        final char[] buffer = new char[10];

        assertThatThrownBy(() -> CharIlaDecimate.create(null, 2, buffer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ila == null not allowed!");
        assertThatThrownBy(() -> CharIlaDecimate.create(ila, 2, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("buffer == null not allowed!");
        assertThatThrownBy(() -> CharIlaDecimate.create(ila, 1, buffer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("factor (=1) < 2 not allowed!");
        assertThatThrownBy(() -> CharIlaDecimate.create(ila, 2, new char[0]))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("buffer.length (=0) < 1 not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final char[] array = new char[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = (char) random.nextInt();
        }
        CharIla ila = CharIlaFromArray.create(array);
        for (int factor = 2; factor <= length; ++factor) {
            final int targetLength = (length + factor - 1) / factor;
            final char[] target = new char[targetLength];
            for (int ii = 0; ii < target.length; ++ii) {
                target[ii] = array[ii * factor];
            }
            CharIla targetIla = CharIlaFromArray.create(target);
            CharIla actualIla = CharIlaDecimate.create(ila, factor, new char[100]);

            CharIlaCheck.check(targetIla, actualIla);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
