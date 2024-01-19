package tfw.immutable.ila.charila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class CharIlaInsertTest {
    @Test
    void testArguments() throws Exception {
        final Random random = new Random(0);
        final CharIla ila = CharIlaFromArray.create(new char[10]);
        final long ilaLength = ila.length();
        final char value = (char) random.nextInt();

        assertThrows(IllegalArgumentException.class, () -> CharIlaInsert.create(null, 0, value));
        assertThrows(IllegalArgumentException.class, () -> CharIlaInsert.create(ila, -1, value));
        assertThrows(IllegalArgumentException.class, () -> CharIlaInsert.create(ila, ilaLength + 1, value));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final char[] array = new char[length];
        final char[] target = new char[length + 1];
        for (int index = 0; index < length; ++index) {
            final char value = (char) random.nextInt();
            int skipit = 0;
            for (int ii = 0; ii < array.length; ++ii) {
                if (index == ii) {
                    skipit = 1;
                    target[ii] = value;
                }
                target[ii + skipit] = array[ii] = (char) random.nextInt();
            }
            CharIla origIla = CharIlaFromArray.create(array);
            CharIla targetIla = CharIlaFromArray.create(target);
            CharIla actualIla = CharIlaInsert.create(origIla, index, value);
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
