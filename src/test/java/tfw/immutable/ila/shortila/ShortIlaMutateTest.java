package tfw.immutable.ila.shortila;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

/**
 *
 * @immutables.types=all
 */
class ShortIlaMutateTest {
    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final short[] array = new short[length];
        final short[] target = new short[length];
        for (int index = 0; index < length; ++index) {
            for (int ii = 0; ii < array.length; ++ii) {
                array[ii] = target[ii] = (short) random.nextInt();
            }
            final short value = (short) random.nextInt();
            target[index] = value;
            ShortIla origIla = ShortIlaFromArray.create(array);
            ShortIla targetIla = ShortIlaFromArray.create(target);
            ShortIla actualIla = ShortIlaMutate.create(origIla, index, value);
            final short epsilon = (short) 0;
            ShortIlaCheck.checkAll(
                    targetIla,
                    actualIla,
                    IlaTestDimensions.defaultOffsetLength(),
                    IlaTestDimensions.defaultMaxStride(),
                    epsilon);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
