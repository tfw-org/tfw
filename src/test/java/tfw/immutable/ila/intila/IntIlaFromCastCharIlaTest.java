package tfw.immutable.ila.intila;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.charila.CharIla;
import tfw.immutable.ila.charila.CharIlaFromArray;

/**
 *
 * @immutables.types=numericnotchar
 */
class IntIlaFromCastCharIlaTest {
    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final char[] array = new char[length];
        final int[] target = new int[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = (char) random.nextInt();
            target[ii] = (int) array[ii];
        }
        CharIla ila = CharIlaFromArray.create(array);
        IntIla targetIla = IntIlaFromArray.create(target);
        IntIla actualIla = IntIlaFromCastCharIla.create(ila);
        final int epsilon = (int) 0.0;
        IntIlaCheck.checkAll(
                targetIla,
                actualIla,
                IlaTestDimensions.defaultOffsetLength(),
                IlaTestDimensions.defaultMaxStride(),
                epsilon);
    }
}
// AUTO GENERATED FROM TEMPLATE
