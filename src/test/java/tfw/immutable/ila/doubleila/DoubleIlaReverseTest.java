package tfw.immutable.ila.doubleila;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

/**
 *
 * @immutables.types=all
 */
class DoubleIlaReverseTest {
    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final double[] array = new double[length];
        final double[] reversed = new double[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = random.nextDouble();
        }
        for (int ii = 0; ii < reversed.length; ++ii) {
            reversed[ii] = array[length - 1 - ii];
        }
        DoubleIla origIla = DoubleIlaFromArray.create(array);
        DoubleIla targetIla = DoubleIlaFromArray.create(reversed);
        DoubleIla actualIla = DoubleIlaReverse.create(origIla);
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
