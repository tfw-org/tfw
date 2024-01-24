package tfw.immutable.ila.byteila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class ByteIlaInsertTest {
    @Test
    void testArguments() throws Exception {
        final Random random = new Random(0);
        final ByteIla ila = ByteIlaFromArray.create(new byte[10]);
        final long ilaLength = ila.length();
        final byte value = (byte) random.nextInt();

        assertThrows(IllegalArgumentException.class, () -> ByteIlaInsert.create(null, 0, value));
        assertThrows(IllegalArgumentException.class, () -> ByteIlaInsert.create(ila, -1, value));
        assertThrows(IllegalArgumentException.class, () -> ByteIlaInsert.create(ila, ilaLength + 1, value));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final byte[] array = new byte[length];
        final byte[] target = new byte[length + 1];
        for (int index = 0; index < length; ++index) {
            final byte value = (byte) random.nextInt();
            int skipit = 0;
            for (int ii = 0; ii < array.length; ++ii) {
                if (index == ii) {
                    skipit = 1;
                    target[ii] = value;
                }
                target[ii + skipit] = array[ii] = (byte) random.nextInt();
            }
            ByteIla origIla = ByteIlaFromArray.create(array);
            ByteIla targetIla = ByteIlaFromArray.create(target);
            ByteIla actualIla = ByteIlaInsert.create(origIla, index, value);

            ByteIlaCheck.check(targetIla, actualIla);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
