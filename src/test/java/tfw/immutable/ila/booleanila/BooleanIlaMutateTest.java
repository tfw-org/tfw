package tfw.immutable.ila.booleanila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class BooleanIlaMutateTest {
    @Test
    void testArguments() throws Exception {
        final Random random = new Random(0);
        final BooleanIla ila = BooleanIlaFromArray.create(new boolean[10]);
        final long ilaLength = ila.length();
        final boolean value = random.nextBoolean();

        assertThrows(IllegalArgumentException.class, () -> BooleanIlaMutate.create(null, 0, value));
        assertThrows(IllegalArgumentException.class, () -> BooleanIlaMutate.create(ila, -1, value));
        assertThrows(IllegalArgumentException.class, () -> BooleanIlaMutate.create(ila, ilaLength, value));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final boolean[] array = new boolean[length];
        final boolean[] target = new boolean[length];
        for (int index = 0; index < length; ++index) {
            for (int ii = 0; ii < array.length; ++ii) {
                array[ii] = target[ii] = random.nextBoolean();
            }
            final boolean value = random.nextBoolean();
            target[index] = value;
            BooleanIla origIla = BooleanIlaFromArray.create(array);
            BooleanIla targetIla = BooleanIlaFromArray.create(target);
            BooleanIla actualIla = BooleanIlaMutate.create(origIla, index, value);

            BooleanIlaCheck.check(targetIla, actualIla);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
