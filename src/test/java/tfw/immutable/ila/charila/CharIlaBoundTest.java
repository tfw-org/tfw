package tfw.immutable.ila.charila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class CharIlaBoundTest {
    @Test
    void argumentsTest() {
        final char low = (char) 5;
        final char high = (char) 10;
        final CharIla ila = CharIlaFromArray.create(new char[10]);

        assertThatThrownBy(() -> CharIlaBound.create(null, low, high))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ila == null not allowed!");
        assertThatThrownBy(() -> CharIlaBound.create(ila, high, low))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("minimum (=" + (int) high + ") > maximum (=" + (int) low + ") not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final char[] array = new char[length];
        final char[] target = new char[length];
        char minimum = (char) random.nextInt();
        char maximum = (char) random.nextInt();
        if (minimum > maximum) {
            char tmp = minimum;
            minimum = maximum;
            maximum = tmp;
        }
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = (char) random.nextInt();
            target[ii] = array[ii];
            if (target[ii] < minimum) {
                target[ii] = minimum;
            } else if (target[ii] > maximum) {
                target[ii] = maximum;
            }
        }
        CharIla ila = CharIlaFromArray.create(array);
        CharIla targetIla = CharIlaFromArray.create(target);
        CharIla actualIla = CharIlaBound.create(ila, minimum, maximum);

        CharIlaCheck.check(targetIla, actualIla);
    }
}
// AUTO GENERATED FROM TEMPLATE
