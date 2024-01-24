package tfw.immutable.ila.shortila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class ShortIlaRemoveTest {
    @Test
    void testArguments() throws Exception {
        final ShortIla ila = ShortIlaFromArray.create(new short[10]);
        final long ilaLength = ila.length();

        assertThrows(IllegalArgumentException.class, () -> ShortIlaRemove.create(null, 0));
        assertThrows(IllegalArgumentException.class, () -> ShortIlaRemove.create(ila, -1));
        assertThrows(IllegalArgumentException.class, () -> ShortIlaRemove.create(ila, ilaLength));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final short[] array = new short[length];
        final short[] target = new short[length - 1];
        for (int index = 0; index < length; ++index) {
            int targetii = 0;
            for (int ii = 0; ii < array.length; ++ii) {
                array[ii] = (short) random.nextInt();
                if (ii != index) {
                    target[targetii++] = array[ii];
                }
            }
            ShortIla origIla = ShortIlaFromArray.create(array);
            ShortIla targetIla = ShortIlaFromArray.create(target);
            ShortIla actualIla = ShortIlaRemove.create(origIla, index);

            ShortIlaCheck.check(targetIla, actualIla);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
