package tfw.immutable.ila.booleanila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class BooleanIlaFillTest {
    @Test
    void testArguments() {
        final Random random = new Random(0);
        final boolean value = random.nextBoolean();

        assertThrows(IllegalArgumentException.class, () -> BooleanIlaFill.create(value, -1));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final boolean value = random.nextBoolean();
        final int length = IlaTestDimensions.defaultIlaLength();
        final boolean[] array = new boolean[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = value;
        }
        BooleanIla targetIla = BooleanIlaFromArray.create(array);
        BooleanIla actualIla = BooleanIlaFill.create(value, length);
        final boolean epsilon = false;
        BooleanIlaCheck.checkAll(
                targetIla,
                actualIla,
                IlaTestDimensions.defaultOffsetLength(),
                IlaTestDimensions.defaultMaxStride(),
                epsilon);
    }
}
// AUTO GENERATED FROM TEMPLATE
