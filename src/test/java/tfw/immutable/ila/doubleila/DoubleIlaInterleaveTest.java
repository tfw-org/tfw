package tfw.immutable.ila.doubleila;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

/**
 *
 * @immutables.types=all
 */
class DoubleIlaInterleaveTest {
    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        for (int jj = 2; jj < 6; ++jj) {
            final double[][] target = new double[jj][length];
            final double[] array = new double[jj * length];
            for (int ii = 0; ii < jj * length; ++ii) {
                array[ii] = target[ii % jj][ii / jj] = random.nextDouble();
            }
            DoubleIla[] ilas = new DoubleIla[jj];
            for (int ii = 0; ii < jj; ++ii) {
                ilas[ii] = DoubleIlaFromArray.create(target[ii]);
            }
            DoubleIla targetIla = DoubleIlaFromArray.create(array);
            DoubleIla actualIla = DoubleIlaInterleave.create(ilas);
            final double epsilon = 0.0;
            DoubleIlaCheck.checkAll(
                    targetIla,
                    actualIla,
                    IlaTestDimensions.defaultOffsetLength(),
                    IlaTestDimensions.defaultMaxStride(),
                    epsilon);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
