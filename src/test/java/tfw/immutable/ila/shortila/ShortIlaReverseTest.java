package tfw.immutable.ila.shortila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class ShortIlaReverseTest {
    @Test
    void testArguments() {
        final ShortIla ila = ShortIlaFromArray.create(new short[10]);
        final short[] buffer = new short[10];

        assertThrows(IllegalArgumentException.class, () -> ShortIlaReverse.create(null, buffer));
        assertThrows(IllegalArgumentException.class, () -> ShortIlaReverse.create(ila, null));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final short[] array = new short[length];
        final short[] reversed = new short[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = (short) random.nextInt();
        }
        for (int ii = 0; ii < reversed.length; ++ii) {
            reversed[ii] = array[length - 1 - ii];
        }
        ShortIla origIla = ShortIlaFromArray.create(array);
        ShortIla targetIla = ShortIlaFromArray.create(reversed);
        ShortIla actualIla = ShortIlaReverse.create(origIla, new short[1000]);

        ShortIlaCheck.check(targetIla, actualIla);
    }
}
// AUTO GENERATED FROM TEMPLATE
