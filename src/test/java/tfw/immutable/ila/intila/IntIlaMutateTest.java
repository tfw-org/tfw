package tfw.immutable.ila.intila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class IntIlaMutateTest {
    @Test
    void testArguments() throws Exception {
        final Random random = new Random(0);
        final IntIla ila = IntIlaFromArray.create(new int[10]);
        final long ilaLength = ila.length();
        final int value = random.nextInt();

        assertThrows(IllegalArgumentException.class, () -> IntIlaMutate.create(null, 0, value));
        assertThrows(IllegalArgumentException.class, () -> IntIlaMutate.create(ila, -1, value));
        assertThrows(IllegalArgumentException.class, () -> IntIlaMutate.create(ila, ilaLength, value));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final int[] array = new int[length];
        final int[] target = new int[length];
        for (int index = 0; index < length; ++index) {
            for (int ii = 0; ii < array.length; ++ii) {
                array[ii] = target[ii] = random.nextInt();
            }
            final int value = random.nextInt();
            target[index] = value;
            IntIla origIla = IntIlaFromArray.create(array);
            IntIla targetIla = IntIlaFromArray.create(target);
            IntIla actualIla = IntIlaMutate.create(origIla, index, value);

            IntIlaCheck.check(targetIla, actualIla);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
