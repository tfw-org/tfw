package tfw.immutable.ila.shortila;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

/**
 *
 * @immutables.types=numeric
 */
class ShortIlaNegateTest {
    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final short[] array = new short[length];
        final short[] target = new short[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = (short) random.nextInt();
            target[ii] = (short) -array[ii];
        }
        ShortIla ila = ShortIlaFromArray.create(array);
        ShortIla targetIla = ShortIlaFromArray.create(target);
        ShortIla actualIla = ShortIlaNegate.create(ila);
        final short epsilon = (short) 0;
        ShortIlaCheck.checkAll(
                targetIla,
                actualIla,
                IlaTestDimensions.defaultOffsetLength(),
                IlaTestDimensions.defaultMaxStride(),
                epsilon);
    }
}
// AUTO GENERATED FROM TEMPLATE
