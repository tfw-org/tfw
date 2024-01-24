package tfw.immutable.ila.booleanila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class BooleanIlaInsertTest {
    @Test
    void testArguments() throws Exception {
        final Random random = new Random(0);
        final BooleanIla ila = BooleanIlaFromArray.create(new boolean[10]);
        final long ilaLength = ila.length();
        final boolean value = random.nextBoolean();

        assertThrows(IllegalArgumentException.class, () -> BooleanIlaInsert.create(null, 0, value));
        assertThrows(IllegalArgumentException.class, () -> BooleanIlaInsert.create(ila, -1, value));
        assertThrows(IllegalArgumentException.class, () -> BooleanIlaInsert.create(ila, ilaLength + 1, value));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final boolean[] array = new boolean[length];
        final boolean[] target = new boolean[length + 1];
        for (int index = 0; index < length; ++index) {
            final boolean value = random.nextBoolean();
            int skipit = 0;
            for (int ii = 0; ii < array.length; ++ii) {
                if (index == ii) {
                    skipit = 1;
                    target[ii] = value;
                }
                target[ii + skipit] = array[ii] = random.nextBoolean();
            }
            BooleanIla origIla = BooleanIlaFromArray.create(array);
            BooleanIla targetIla = BooleanIlaFromArray.create(target);
            BooleanIla actualIla = BooleanIlaInsert.create(origIla, index, value);

            BooleanIlaCheck.check(targetIla, actualIla);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
