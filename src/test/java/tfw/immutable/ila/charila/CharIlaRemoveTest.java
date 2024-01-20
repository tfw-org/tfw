package tfw.immutable.ila.charila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class CharIlaRemoveTest {
    @Test
    void testArguments() throws Exception {
        final CharIla ila = CharIlaFromArray.create(new char[10]);
        final long ilaLength = ila.length();

        assertThrows(IllegalArgumentException.class, () -> CharIlaRemove.create(null, 0));
        assertThrows(IllegalArgumentException.class, () -> CharIlaRemove.create(ila, -1));
        assertThrows(IllegalArgumentException.class, () -> CharIlaRemove.create(ila, ilaLength));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final char[] array = new char[length];
        final char[] target = new char[length - 1];
        for (int index = 0; index < length; ++index) {
            int targetii = 0;
            for (int ii = 0; ii < array.length; ++ii) {
                array[ii] = (char) random.nextInt();
                if (ii != index) {
                    target[targetii++] = array[ii];
                }
            }
            CharIla origIla = CharIlaFromArray.create(array);
            CharIla targetIla = CharIlaFromArray.create(target);
            CharIla actualIla = CharIlaRemove.create(origIla, index);
            final char epsilon = (char) 0;
            CharIlaCheck.checkAll(
                    targetIla,
                    actualIla,
                    IlaTestDimensions.defaultOffsetLength(),
                    IlaTestDimensions.defaultMaxStride(),
                    epsilon);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
