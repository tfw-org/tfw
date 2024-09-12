package tfw.immutable.ila.longila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class LongIlaMutateTest {
    @Test
    void testArguments() throws Exception {
        final Random random = new Random(0);
        final LongIla ila = LongIlaFromArray.create(new long[10]);
        final long ilaLength = ila.length();
        final long value = random.nextLong();

        assertThrows(IllegalArgumentException.class, () -> LongIlaMutate.create(null, 0, value));
        assertThrows(IllegalArgumentException.class, () -> LongIlaMutate.create(ila, -1, value));
        assertThrows(IllegalArgumentException.class, () -> LongIlaMutate.create(ila, ilaLength, value));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final long[] array = new long[length];
        final long[] target = new long[length];
        for (int index = 0; index < length; ++index) {
            for (int ii = 0; ii < array.length; ++ii) {
                array[ii] = target[ii] = random.nextLong();
            }
            final long value = random.nextLong();
            target[index] = value;
            LongIla origIla = LongIlaFromArray.create(array);
            LongIla targetIla = LongIlaFromArray.create(target);
            LongIla actualIla = LongIlaMutate.create(origIla, index, value);

            LongIlaCheck.check(targetIla, actualIla);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
