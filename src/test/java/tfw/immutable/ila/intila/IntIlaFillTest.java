package tfw.immutable.ila.intila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class IntIlaFillTest {
    @Test
    void testArguments() {
        final Random random = new Random(0);
        final int value = random.nextInt();

        assertThrows(IllegalArgumentException.class, () -> IntIlaFill.create(value, -1));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int value = random.nextInt();
        final int length = IlaTestDimensions.defaultIlaLength();
        final int[] array = new int[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = value;
        }
        IntIla targetIla = IntIlaFromArray.create(array);
        IntIla actualIla = IntIlaFill.create(value, length);
        final int epsilon = 0;
        IntIlaCheck.checkAll(
                targetIla,
                actualIla,
                IlaTestDimensions.defaultOffsetLength(),
                IlaTestDimensions.defaultMaxStride(),
                epsilon);
    }
}
// AUTO GENERATED FROM TEMPLATE
