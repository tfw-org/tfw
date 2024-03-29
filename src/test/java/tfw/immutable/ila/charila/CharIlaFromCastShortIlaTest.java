package tfw.immutable.ila.charila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.shortila.ShortIla;
import tfw.immutable.ila.shortila.ShortIlaFromArray;

class CharIlaFromCastShortIlaTest {
    @Test
    void testArguments() {
        final ShortIla ila = ShortIlaFromArray.create(new short[10]);

        assertThrows(IllegalArgumentException.class, () -> CharIlaFromCastShortIla.create(null, 1));
        assertThrows(IllegalArgumentException.class, () -> CharIlaFromCastShortIla.create(ila, 0));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final short[] array = new short[length];
        final char[] target = new char[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = (short) random.nextInt();
            target[ii] = (char) array[ii];
        }
        ShortIla ila = ShortIlaFromArray.create(array);
        CharIla targetIla = CharIlaFromArray.create(target);
        CharIla actualIla = CharIlaFromCastShortIla.create(ila, 100);

        CharIlaCheck.check(targetIla, actualIla);
    }
}
// AUTO GENERATED FROM TEMPLATE
