package tfw.immutable.ila.byteila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class ByteIlaConcatenateTest {
    @Test
    void testArguments() {
        final ByteIla ila = ByteIlaFromArray.create(new byte[10]);

        assertThrows(IllegalArgumentException.class, () -> ByteIlaConcatenate.create(ila, null));
        assertThrows(IllegalArgumentException.class, () -> ByteIlaConcatenate.create(null, ila));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int leftLength = IlaTestDimensions.defaultIlaLength();
        final int rightLength = 1 + random.nextInt(leftLength);
        final byte[] leftArray = new byte[leftLength];
        final byte[] rightArray = new byte[rightLength];
        final byte[] array = new byte[leftLength + rightLength];
        for (int ii = 0; ii < leftArray.length; ++ii) {
            array[ii] = leftArray[ii] = (byte) random.nextInt();
        }
        for (int ii = 0; ii < rightArray.length; ++ii) {
            array[ii + leftLength] = rightArray[ii] = (byte) random.nextInt();
        }
        ByteIla leftIla = ByteIlaFromArray.create(leftArray);
        ByteIla rightIla = ByteIlaFromArray.create(rightArray);
        ByteIla targetIla = ByteIlaFromArray.create(array);
        ByteIla actualIla = ByteIlaConcatenate.create(leftIla, rightIla);

        ByteIlaCheck.check(targetIla, actualIla);
    }
}
// AUTO GENERATED FROM TEMPLATE
