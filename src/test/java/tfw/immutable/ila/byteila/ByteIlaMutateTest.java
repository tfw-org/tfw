package tfw.immutable.ila.byteila;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

/**
 *
 * @immutables.types=all
 */
class ByteIlaMutateTest {
    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final byte[] array = new byte[length];
        final byte[] target = new byte[length];
        for (int index = 0; index < length; ++index) {
            for (int ii = 0; ii < array.length; ++ii) {
                array[ii] = target[ii] = (byte) random.nextInt();
            }
            final byte value = (byte) random.nextInt();
            target[index] = value;
            ByteIla origIla = ByteIlaFromArray.create(array);
            ByteIla targetIla = ByteIlaFromArray.create(target);
            ByteIla actualIla = ByteIlaMutate.create(origIla, index, value);
            final byte epsilon = (byte) 0;
            ByteIlaCheck.checkAll(
                    targetIla,
                    actualIla,
                    IlaTestDimensions.defaultOffsetLength(),
                    IlaTestDimensions.defaultMaxStride(),
                    epsilon);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
