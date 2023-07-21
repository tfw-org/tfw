package tfw.immutable.ila.intila;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.shortila.ShortIla;
import tfw.immutable.ila.shortila.ShortIlaFromArray;

/**
 *
 * @immutables.types=numericnotshort
 */
class IntIlaFromCastShortIlaTest {
    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final short[] array = new short[length];
        final int[] target = new int[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = (short) random.nextInt();
            target[ii] = (int) array[ii];
        }
        ShortIla ila = ShortIlaFromArray.create(array);
        IntIla targetIla = IntIlaFromArray.create(target);
        IntIla actualIla = IntIlaFromCastShortIla.create(ila, 100);
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
