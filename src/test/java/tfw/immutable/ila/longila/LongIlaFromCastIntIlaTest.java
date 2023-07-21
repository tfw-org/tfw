package tfw.immutable.ila.longila;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.intila.IntIla;
import tfw.immutable.ila.intila.IntIlaFromArray;

/**
 *
 * @immutables.types=numericnotint
 */
class LongIlaFromCastIntIlaTest {
    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final int[] array = new int[length];
        final long[] target = new long[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = random.nextInt();
            target[ii] = (long) array[ii];
        }
        IntIla ila = IntIlaFromArray.create(array);
        LongIla targetIla = LongIlaFromArray.create(target);
        LongIla actualIla = LongIlaFromCastIntIla.create(ila, 100);
        final long epsilon = 0L;
        LongIlaCheck.checkAll(
                targetIla,
                actualIla,
                IlaTestDimensions.defaultOffsetLength(),
                IlaTestDimensions.defaultMaxStride(),
                epsilon);
    }
}
// AUTO GENERATED FROM TEMPLATE
