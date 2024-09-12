package tfw.immutable.ila.charila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class CharIlaFillTest {
    @Test
    void testArguments() {
        final Random random = new Random(0);
        final char value = (char) random.nextInt();

        assertThrows(IllegalArgumentException.class, () -> CharIlaFill.create(value, -1));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final char value = (char) random.nextInt();
        final int length = IlaTestDimensions.defaultIlaLength();
        final char[] array = new char[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = value;
        }
        CharIla targetIla = CharIlaFromArray.create(array);
        CharIla actualIla = CharIlaFill.create(value, length);

        CharIlaCheck.check(targetIla, actualIla);
    }
}
// AUTO GENERATED FROM TEMPLATE
