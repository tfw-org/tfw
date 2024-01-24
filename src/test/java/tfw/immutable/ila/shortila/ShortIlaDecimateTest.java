package tfw.immutable.ila.shortila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class ShortIlaDecimateTest {
    @Test
    void testArguments() {
        final ShortIla ila = ShortIlaFromArray.create(new short[10]);
        final short[] buffer = new short[10];

        assertThrows(IllegalArgumentException.class, () -> ShortIlaDecimate.create(null, 2, buffer));
        assertThrows(IllegalArgumentException.class, () -> ShortIlaDecimate.create(ila, 2, null));
        assertThrows(IllegalArgumentException.class, () -> ShortIlaDecimate.create(ila, 1, buffer));
        assertThrows(IllegalArgumentException.class, () -> ShortIlaDecimate.create(ila, 2, new short[0]));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final short[] array = new short[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = (short) random.nextInt();
        }
        ShortIla ila = ShortIlaFromArray.create(array);
        for (int factor = 2; factor <= length; ++factor) {
            final int targetLength = (length + factor - 1) / factor;
            final short[] target = new short[targetLength];
            for (int ii = 0; ii < target.length; ++ii) {
                target[ii] = array[ii * factor];
            }
            ShortIla targetIla = ShortIlaFromArray.create(target);
            ShortIla actualIla = ShortIlaDecimate.create(ila, factor, new short[100]);

            ShortIlaCheck.check(targetIla, actualIla);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
