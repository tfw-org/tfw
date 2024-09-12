package tfw.immutable.ila.byteila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class ByteIlaAddTest {
    @Test
    void testArguments() {
        final ByteIla ila1 = ByteIlaFromArray.create(new byte[10]);
        final ByteIla ila2 = ByteIlaFromArray.create(new byte[20]);

        assertThrows(IllegalArgumentException.class, () -> ByteIlaAdd.create(null, ila1, 10));
        assertThrows(IllegalArgumentException.class, () -> ByteIlaAdd.create(ila1, null, 10));
        assertThrows(IllegalArgumentException.class, () -> ByteIlaAdd.create(ila1, ila1, 0));
        assertThrows(IllegalArgumentException.class, () -> ByteIlaAdd.create(ila1, ila2, 10));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final byte[] leftArray = new byte[length];
        final byte[] rightArray = new byte[length];
        final byte[] array = new byte[length];
        for (int ii = 0; ii < leftArray.length; ++ii) {
            leftArray[ii] = (byte) random.nextInt();
            rightArray[ii] = (byte) random.nextInt();
            array[ii] = (byte) (leftArray[ii] + rightArray[ii]);
        }
        ByteIla leftIla = ByteIlaFromArray.create(leftArray);
        ByteIla rightIla = ByteIlaFromArray.create(rightArray);
        ByteIla targetIla = ByteIlaFromArray.create(array);
        ByteIla actualIla = ByteIlaAdd.create(leftIla, rightIla, 100);

        ByteIlaCheck.check(targetIla, actualIla);
    }
}
// AUTO GENERATED FROM TEMPLATE
