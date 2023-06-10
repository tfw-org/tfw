package tfw.immutable.ila.intila;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

/**
 *
 * @immutables.types=all
 */
class IntIlaInsertTest {
    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final int[] array = new int[length];
        final int[] target = new int[length + 1];
        for (int index = 0; index < length; ++index) {
            final int value = random.nextInt();
            int skipit = 0;
            for (int ii = 0; ii < array.length; ++ii) {
                if (index == ii) {
                    skipit = 1;
                    target[ii] = value;
                }
                target[ii + skipit] = array[ii] = random.nextInt();
            }
            IntIla origIla = IntIlaFromArray.create(array);
            IntIla targetIla = IntIlaFromArray.create(target);
            IntIla actualIla = IntIlaInsert.create(origIla, index, value);
            final int epsilon = 0;
            IntIlaCheck.checkAll(
                    targetIla,
                    actualIla,
                    IlaTestDimensions.defaultOffsetLength(),
                    IlaTestDimensions.defaultMaxStride(),
                    epsilon);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
