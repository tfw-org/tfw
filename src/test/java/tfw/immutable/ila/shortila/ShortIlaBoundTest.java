package tfw.immutable.ila.shortila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class ShortIlaBoundTest {
    @Test
    void testArguments() {
        final ShortIla ila = ShortIlaFromArray.create(new short[10]);

        assertThrows(IllegalArgumentException.class, () -> ShortIlaBound.create(null, (short) 5, (short) 10));
        assertThrows(IllegalArgumentException.class, () -> ShortIlaBound.create(ila, (short) 10, (short) 5));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final short[] array = new short[length];
        final short[] target = new short[length];
        short minimum = (short) random.nextInt();
        short maximum = (short) random.nextInt();
        if (minimum > maximum) {
            short tmp = minimum;
            minimum = maximum;
            maximum = tmp;
        }
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = (short) random.nextInt();
            target[ii] = array[ii];
            if (target[ii] < minimum) {
                target[ii] = minimum;
            } else if (target[ii] > maximum) {
                target[ii] = maximum;
            }
        }
        ShortIla ila = ShortIlaFromArray.create(array);
        ShortIla targetIla = ShortIlaFromArray.create(target);
        ShortIla actualIla = ShortIlaBound.create(ila, minimum, maximum);

        ShortIlaCheck.check(targetIla, actualIla);
    }
}
// AUTO GENERATED FROM TEMPLATE
