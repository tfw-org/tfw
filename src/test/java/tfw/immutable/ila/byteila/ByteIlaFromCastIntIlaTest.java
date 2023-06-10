package tfw.immutable.ila.byteila;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.intila.IntIla;
import tfw.immutable.ila.intila.IntIlaFromArray;

/**
 *
 * @immutables.types=numericnotint
 */
class ByteIlaFromCastIntIlaTest {
    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final int[] array = new int[length];
        final byte[] target = new byte[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = random.nextInt();
            target[ii] = (byte) array[ii];
        }
        IntIla ila = IntIlaFromArray.create(array);
        ByteIla targetIla = ByteIlaFromArray.create(target);
        ByteIla actualIla = ByteIlaFromCastIntIla.create(ila);
        final byte epsilon = (byte) 0.0;
        ByteIlaCheck.checkAll(
                targetIla,
                actualIla,
                IlaTestDimensions.defaultOffsetLength(),
                IlaTestDimensions.defaultMaxStride(),
                epsilon);
    }
}
// AUTO GENERATED FROM TEMPLATE
