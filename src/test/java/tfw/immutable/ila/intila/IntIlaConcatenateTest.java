package tfw.immutable.ila.intila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class IntIlaConcatenateTest {
    @Test
    void testArguments() {
        final IntIla ila = IntIlaFromArray.create(new int[10]);

        assertThrows(IllegalArgumentException.class, () -> IntIlaConcatenate.create(ila, null));
        assertThrows(IllegalArgumentException.class, () -> IntIlaConcatenate.create(null, ila));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int leftLength = IlaTestDimensions.defaultIlaLength();
        final int rightLength = 1 + random.nextInt(leftLength);
        final int[] leftArray = new int[leftLength];
        final int[] rightArray = new int[rightLength];
        final int[] array = new int[leftLength + rightLength];
        for (int ii = 0; ii < leftArray.length; ++ii) {
            array[ii] = leftArray[ii] = random.nextInt();
        }
        for (int ii = 0; ii < rightArray.length; ++ii) {
            array[ii + leftLength] = rightArray[ii] = random.nextInt();
        }
        IntIla leftIla = IntIlaFromArray.create(leftArray);
        IntIla rightIla = IntIlaFromArray.create(rightArray);
        IntIla targetIla = IntIlaFromArray.create(array);
        IntIla actualIla = IntIlaConcatenate.create(leftIla, rightIla);

        IntIlaCheck.check(targetIla, actualIla);
    }
}
// AUTO GENERATED FROM TEMPLATE
