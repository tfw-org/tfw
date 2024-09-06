package tfw.immutable.ila.charila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class CharIlaNegateTest {
    @Test
    void testArguments() throws Exception {
        assertThrows(IllegalArgumentException.class, () -> CharIlaNegate.create(null));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final char[] array = new char[length];
        final char[] target = new char[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = (char) random.nextInt();
            target[ii] = (char) -array[ii];
        }
        CharIla ila = CharIlaFromArray.create(array);
        CharIla targetIla = CharIlaFromArray.create(target);
        CharIla actualIla = CharIlaNegate.create(ila);

        CharIlaCheck.check(targetIla, actualIla);
    }
}
// AUTO GENERATED FROM TEMPLATE
