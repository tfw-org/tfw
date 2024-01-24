package tfw.immutable.ila.longila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class LongIlaInsertTest {
    @Test
    void testArguments() throws Exception {
        final Random random = new Random(0);
        final LongIla ila = LongIlaFromArray.create(new long[10]);
        final long ilaLength = ila.length();
        final long value = random.nextLong();

        assertThrows(IllegalArgumentException.class, () -> LongIlaInsert.create(null, 0, value));
        assertThrows(IllegalArgumentException.class, () -> LongIlaInsert.create(ila, -1, value));
        assertThrows(IllegalArgumentException.class, () -> LongIlaInsert.create(ila, ilaLength + 1, value));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final long[] array = new long[length];
        final long[] target = new long[length + 1];
        for (int index = 0; index < length; ++index) {
            final long value = random.nextLong();
            int skipit = 0;
            for (int ii = 0; ii < array.length; ++ii) {
                if (index == ii) {
                    skipit = 1;
                    target[ii] = value;
                }
                target[ii + skipit] = array[ii] = random.nextLong();
            }
            LongIla origIla = LongIlaFromArray.create(array);
            LongIla targetIla = LongIlaFromArray.create(target);
            LongIla actualIla = LongIlaInsert.create(origIla, index, value);

            LongIlaCheck.check(targetIla, actualIla);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
