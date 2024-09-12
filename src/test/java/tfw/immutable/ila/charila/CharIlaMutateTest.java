package tfw.immutable.ila.charila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class CharIlaMutateTest {
    @Test
    void testArguments() throws Exception {
        final Random random = new Random(0);
        final CharIla ila = CharIlaFromArray.create(new char[10]);
        final long ilaLength = ila.length();
        final char value = (char) random.nextInt();

        assertThrows(IllegalArgumentException.class, () -> CharIlaMutate.create(null, 0, value));
        assertThrows(IllegalArgumentException.class, () -> CharIlaMutate.create(ila, -1, value));
        assertThrows(IllegalArgumentException.class, () -> CharIlaMutate.create(ila, ilaLength, value));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final char[] array = new char[length];
        final char[] target = new char[length];
        for (int index = 0; index < length; ++index) {
            for (int ii = 0; ii < array.length; ++ii) {
                array[ii] = target[ii] = (char) random.nextInt();
            }
            final char value = (char) random.nextInt();
            target[index] = value;
            CharIla origIla = CharIlaFromArray.create(array);
            CharIla targetIla = CharIlaFromArray.create(target);
            CharIla actualIla = CharIlaMutate.create(origIla, index, value);

            CharIlaCheck.check(targetIla, actualIla);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
