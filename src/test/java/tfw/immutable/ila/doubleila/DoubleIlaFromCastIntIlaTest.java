package tfw.immutable.ila.doubleila;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.intila.IntIla;
import tfw.immutable.ila.intila.IntIlaFromArray;

/**
 *
 * @immutables.types=numericnotint
 */
class DoubleIlaFromCastIntIlaTest {
    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final int[] array = new int[length];
        final double[] target = new double[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = random.nextInt();
            target[ii] = (double) array[ii];
        }
        IntIla ila = IntIlaFromArray.create(array);
        DoubleIla targetIla = DoubleIlaFromArray.create(target);
        DoubleIla actualIla = DoubleIlaFromCastIntIla.create(ila, 100);
        final double epsilon = 0.0;
        DoubleIlaCheck.checkAll(
                targetIla,
                actualIla,
                IlaTestDimensions.defaultOffsetLength(),
                IlaTestDimensions.defaultMaxStride(),
                epsilon);
    }
}
// AUTO GENERATED FROM TEMPLATE
