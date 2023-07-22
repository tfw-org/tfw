package tfw.immutable.ila.booleanila;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class BooleanIlaDecimateTest {
    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final boolean[] array = new boolean[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = random.nextBoolean();
        }
        BooleanIla ila = BooleanIlaFromArray.create(array);
        for (int factor = 2; factor <= length; ++factor) {
            final int targetLength = (length + factor - 1) / factor;
            final boolean[] target = new boolean[targetLength];
            for (int ii = 0; ii < target.length; ++ii) {
                target[ii] = array[ii * factor];
            }
            BooleanIla targetIla = BooleanIlaFromArray.create(target);
            BooleanIla actualIla = BooleanIlaDecimate.create(ila, factor, new boolean[100]);
            final boolean epsilon = false;
            BooleanIlaCheck.checkAll(
                    targetIla,
                    actualIla,
                    IlaTestDimensions.defaultOffsetLength(),
                    IlaTestDimensions.defaultMaxStride(),
                    epsilon);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
