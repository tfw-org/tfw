package tfw.immutable.ila.longila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class LongIlaFillTest {
    @Test
    void testArguments() {
        final Random random = new Random(0);
        final long value = random.nextLong();

        assertThrows(IllegalArgumentException.class, () -> LongIlaFill.create(value, -1));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final long value = random.nextLong();
        final int length = IlaTestDimensions.defaultIlaLength();
        final long[] array = new long[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = value;
        }
        LongIla targetIla = LongIlaFromArray.create(array);
        LongIla actualIla = LongIlaFill.create(value, length);

        LongIlaCheck.check(targetIla, actualIla);
    }
}
// AUTO GENERATED FROM TEMPLATE
