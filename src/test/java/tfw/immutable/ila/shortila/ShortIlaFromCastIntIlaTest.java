package tfw.immutable.ila.shortila;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.intila.IntIla;
import tfw.immutable.ila.intila.IntIlaFromArray;

/**
 *
 * @immutables.types=numericnotint
 */
class ShortIlaFromCastIntIlaTest {
    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final int[] array = new int[length];
        final short[] target = new short[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = random.nextInt();
            target[ii] = (short) array[ii];
        }
        IntIla ila = IntIlaFromArray.create(array);
        ShortIla targetIla = ShortIlaFromArray.create(target);
        ShortIla actualIla = ShortIlaFromCastIntIla.create(ila);
        final short epsilon = (short) 0.0;
        ShortIlaCheck.checkAll(
                targetIla,
                actualIla,
                IlaTestDimensions.defaultOffsetLength(),
                IlaTestDimensions.defaultMaxStride(),
                epsilon);
    }
}
// AUTO GENERATED FROM TEMPLATE
