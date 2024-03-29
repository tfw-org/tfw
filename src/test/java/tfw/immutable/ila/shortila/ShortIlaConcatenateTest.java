package tfw.immutable.ila.shortila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class ShortIlaConcatenateTest {
    @Test
    void testArguments() {
        final ShortIla ila = ShortIlaFromArray.create(new short[10]);

        assertThrows(IllegalArgumentException.class, () -> ShortIlaConcatenate.create(ila, null));
        assertThrows(IllegalArgumentException.class, () -> ShortIlaConcatenate.create(null, ila));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int leftLength = IlaTestDimensions.defaultIlaLength();
        final int rightLength = 1 + random.nextInt(leftLength);
        final short[] leftArray = new short[leftLength];
        final short[] rightArray = new short[rightLength];
        final short[] array = new short[leftLength + rightLength];
        for (int ii = 0; ii < leftArray.length; ++ii) {
            array[ii] = leftArray[ii] = (short) random.nextInt();
        }
        for (int ii = 0; ii < rightArray.length; ++ii) {
            array[ii + leftLength] = rightArray[ii] = (short) random.nextInt();
        }
        ShortIla leftIla = ShortIlaFromArray.create(leftArray);
        ShortIla rightIla = ShortIlaFromArray.create(rightArray);
        ShortIla targetIla = ShortIlaFromArray.create(array);
        ShortIla actualIla = ShortIlaConcatenate.create(leftIla, rightIla);

        ShortIlaCheck.check(targetIla, actualIla);
    }
}
// AUTO GENERATED FROM TEMPLATE
