package tfw.immutable.ila.floatila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.shortila.ShortIla;
import tfw.immutable.ila.shortila.ShortIlaFromArray;

class FloatIlaFromCastShortIlaTest {
    @Test
    void testArguments() {
        final ShortIla ila = ShortIlaFromArray.create(new short[10]);

        assertThrows(IllegalArgumentException.class, () -> FloatIlaFromCastShortIla.create(null, 1));
        assertThrows(IllegalArgumentException.class, () -> FloatIlaFromCastShortIla.create(ila, 0));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final short[] array = new short[length];
        final float[] target = new float[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = (short) random.nextInt();
            target[ii] = (float) array[ii];
        }
        ShortIla ila = ShortIlaFromArray.create(array);
        FloatIla targetIla = FloatIlaFromArray.create(target);
        FloatIla actualIla = FloatIlaFromCastShortIla.create(ila, 100);

        FloatIlaCheck.check(targetIla, actualIla);
    }
}
// AUTO GENERATED FROM TEMPLATE
