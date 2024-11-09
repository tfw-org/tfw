package tfw.immutable.ila.intila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class IntIlaRampTest {
    @Test
    void testArguments() {
        final Random random = new Random(0);
        final int start = random.nextInt();
        final int increment = random.nextInt();

        assertThrows(IllegalArgumentException.class, () -> IntIlaRamp.create(start, increment, -1));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int startValue = random.nextInt();
        final int increment = random.nextInt();
        final int length = IlaTestDimensions.defaultIlaLength();
        final int[] array = new int[length];
        int value = startValue;
        for (int ii = 0; ii < array.length; ++ii, value += increment) {
            array[ii] = value;
        }
        IntIla targetIla = IntIlaFromArray.create(array);
        IntIla actualIla = IntIlaRamp.create(startValue, increment, length);
        final int epsilon = (int) 0.000001;
        IntIlaCheck.checkAll(
                targetIla,
                actualIla,
                IlaTestDimensions.defaultOffsetLength(),
                IlaTestDimensions.defaultMaxStride(),
                epsilon);
    }
}
// AUTO GENERATED FROM TEMPLATE
