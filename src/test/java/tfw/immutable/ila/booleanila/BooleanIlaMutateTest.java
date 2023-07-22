package tfw.immutable.ila.booleanila;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class BooleanIlaMutateTest {
    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final boolean[] array = new boolean[length];
        final boolean[] target = new boolean[length];
        for (int index = 0; index < length; ++index) {
            for (int ii = 0; ii < array.length; ++ii) {
                array[ii] = target[ii] = random.nextBoolean();
            }
            final boolean value = random.nextBoolean();
            target[index] = value;
            BooleanIla origIla = BooleanIlaFromArray.create(array);
            BooleanIla targetIla = BooleanIlaFromArray.create(target);
            BooleanIla actualIla = BooleanIlaMutate.create(origIla, index, value);
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
