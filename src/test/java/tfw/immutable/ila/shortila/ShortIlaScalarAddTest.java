package tfw.immutable.ila.shortila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class ShortIlaScalarAddTest {
    @Test
    void testArguments() {
        final Random random = new Random(0);
        final short value = (short) random.nextInt();

        assertThrows(IllegalArgumentException.class, () -> ShortIlaScalarAdd.create(null, value));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final short scalar = (short) random.nextInt();
        final short[] array = new short[length];
        final short[] target = new short[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = (short) random.nextInt();
            target[ii] = (short) (array[ii] + scalar);
        }
        ShortIla ila = ShortIlaFromArray.create(array);
        ShortIla targetIla = ShortIlaFromArray.create(target);
        ShortIla actualIla = ShortIlaScalarAdd.create(ila, scalar);

        ShortIlaCheck.check(targetIla, actualIla);
    }
}
// AUTO GENERATED FROM TEMPLATE
