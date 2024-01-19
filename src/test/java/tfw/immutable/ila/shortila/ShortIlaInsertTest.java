package tfw.immutable.ila.shortila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class ShortIlaInsertTest {
    @Test
    void testArguments() throws Exception {
        final Random random = new Random(0);
        final ShortIla ila = ShortIlaFromArray.create(new short[10]);
        final long ilaLength = ila.length();
        final short value = (short) random.nextInt();

        assertThrows(IllegalArgumentException.class, () -> ShortIlaInsert.create(null, 0, value));
        assertThrows(IllegalArgumentException.class, () -> ShortIlaInsert.create(ila, -1, value));
        assertThrows(IllegalArgumentException.class, () -> ShortIlaInsert.create(ila, ilaLength + 1, value));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final short[] array = new short[length];
        final short[] target = new short[length + 1];
        for (int index = 0; index < length; ++index) {
            final short value = (short) random.nextInt();
            int skipit = 0;
            for (int ii = 0; ii < array.length; ++ii) {
                if (index == ii) {
                    skipit = 1;
                    target[ii] = value;
                }
                target[ii + skipit] = array[ii] = (short) random.nextInt();
            }
            ShortIla origIla = ShortIlaFromArray.create(array);
            ShortIla targetIla = ShortIlaFromArray.create(target);
            ShortIla actualIla = ShortIlaInsert.create(origIla, index, value);
            final short epsilon = (short) 0;
            ShortIlaCheck.checkAll(
                    targetIla,
                    actualIla,
                    IlaTestDimensions.defaultOffsetLength(),
                    IlaTestDimensions.defaultMaxStride(),
                    epsilon);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
