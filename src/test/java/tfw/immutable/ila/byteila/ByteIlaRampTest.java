package tfw.immutable.ila.byteila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class ByteIlaRampTest {
    @Test
    void testArguments() throws Exception {
        final Random random = new Random(0);
        final byte start = (byte) random.nextInt();
        final byte increment = (byte) random.nextInt();

        assertThrows(IllegalArgumentException.class, () -> ByteIlaRamp.create(start, increment, -1));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final byte startValue = (byte) random.nextInt();
        final byte increment = (byte) random.nextInt();
        final int length = IlaTestDimensions.defaultIlaLength();
        final byte[] array = new byte[length];
        byte value = startValue;
        for (int ii = 0; ii < array.length; ++ii, value += increment) {
            array[ii] = value;
        }
        ByteIla targetIla = ByteIlaFromArray.create(array);
        ByteIla actualIla = ByteIlaRamp.create(startValue, increment, length);
        final byte epsilon = (byte) (0.000001);
        ByteIlaCheck.checkAll(
                targetIla,
                actualIla,
                IlaTestDimensions.defaultOffsetLength(),
                IlaTestDimensions.defaultMaxStride(),
                epsilon);
    }
}
// AUTO GENERATED FROM TEMPLATE
