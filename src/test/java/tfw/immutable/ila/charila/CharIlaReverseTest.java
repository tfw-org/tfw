package tfw.immutable.ila.charila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class CharIlaReverseTest {
    @Test
    void testArguments() {
        final CharIla ila = CharIlaFromArray.create(new char[10]);
        final char[] buffer = new char[10];

        assertThrows(IllegalArgumentException.class, () -> CharIlaReverse.create(null, buffer));
        assertThrows(IllegalArgumentException.class, () -> CharIlaReverse.create(ila, null));
    }

    @Test
    void testAll() throws Exception {
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
