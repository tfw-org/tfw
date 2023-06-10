package tfw.immutable.ila.byteila;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

/**
 *
 * @immutables.types=numeric
 */
class ByteIlaBoundTest {
    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final byte[] array = new byte[length];
        final byte[] target = new byte[length];
        byte minimum = (byte) random.nextInt();
        byte maximum = (byte) random.nextInt();
        if (minimum > maximum) {
            byte tmp = minimum;
            minimum = maximum;
            maximum = tmp;
        }
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = (byte) random.nextInt();
            target[ii] = array[ii];
            if (target[ii] < minimum) {
                target[ii] = minimum;
            } else if (target[ii] > maximum) {
                target[ii] = maximum;
            }
        }
        ByteIla ila = ByteIlaFromArray.create(array);
        ByteIla targetIla = ByteIlaFromArray.create(target);
        ByteIla actualIla = ByteIlaBound.create(ila, minimum, maximum);
        final byte epsilon = (byte) 0.0;
        ByteIlaCheck.checkAll(
                targetIla,
                actualIla,
                IlaTestDimensions.defaultOffsetLength(),
                IlaTestDimensions.defaultMaxStride(),
                epsilon);
    }
}
// AUTO GENERATED FROM TEMPLATE
