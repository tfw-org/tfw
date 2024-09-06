package tfw.immutable.ila.charila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class CharIlaBoundTest {
    @Test
    void testArguments() {
        final CharIla ila = CharIlaFromArray.create(new char[10]);

        assertThrows(IllegalArgumentException.class, () -> CharIlaBound.create(null, (char) 5, (char) 10));
        assertThrows(IllegalArgumentException.class, () -> CharIlaBound.create(ila, (char) 10, (char) 5));
    }

    @Test
    void testAll() throws Exception {
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
