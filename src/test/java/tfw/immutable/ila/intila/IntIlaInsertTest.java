package tfw.immutable.ila.intila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class IntIlaInsertTest {
    @Test
    void testArguments() throws Exception {
        final Random random = new Random(0);
        final IntIla ila = IntIlaFromArray.create(new int[10]);
        final long ilaLength = ila.length();
        final int value = random.nextInt();

        assertThrows(IllegalArgumentException.class, () -> IntIlaInsert.create(null, 0, value));
        assertThrows(IllegalArgumentException.class, () -> IntIlaInsert.create(ila, -1, value));
        assertThrows(IllegalArgumentException.class, () -> IntIlaInsert.create(ila, ilaLength + 1, value));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final int[] array = new int[length];
        final int[] target = new int[length + 1];
        for (int index = 0; index < length; ++index) {
            final int value = random.nextInt();
            int skipit = 0;
            for (int ii = 0; ii < array.length; ++ii) {
                if (index == ii) {
                    skipit = 1;
                    target[ii] = value;
                }
                target[ii + skipit] = array[ii] = random.nextInt();
            }
            IntIla origIla = IntIlaFromArray.create(array);
            IntIla targetIla = IntIlaFromArray.create(target);
            IntIla actualIla = IntIlaInsert.create(origIla, index, value);

            IntIlaCheck.check(targetIla, actualIla);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
