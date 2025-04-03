package tfw.immutable.ila.charila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class CharIlaReverseTest {
    @Test
    void argumentsTest() {
        final CharIla ila = CharIlaFromArray.create(new char[10]);
        final char[] buffer = new char[10];

        assertThatThrownBy(() -> CharIlaReverse.create(null, buffer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ila == null not allowed!");
        assertThatThrownBy(() -> CharIlaReverse.create(ila, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("buffer == null not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final char[] array = new char[length];
        final char[] reversed = new char[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = (char) random.nextInt();
        }
        for (int ii = 0; ii < reversed.length; ++ii) {
            reversed[ii] = array[length - 1 - ii];
        }
        CharIla origIla = CharIlaFromArray.create(array);
        CharIla targetIla = CharIlaFromArray.create(reversed);
        CharIla actualIla = CharIlaReverse.create(origIla, new char[1000]);

        CharIlaCheck.check(targetIla, actualIla);
    }
}
// AUTO GENERATED FROM TEMPLATE
