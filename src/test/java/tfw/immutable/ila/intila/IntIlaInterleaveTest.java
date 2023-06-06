package tfw.immutable.ila.intila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;

/**
 *
 * @immutables.types=all
 */
public class IntIlaInterleaveTest extends TestCase {
    public void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        for (int jj = 2; jj < 6; ++jj) {
            final int[][] target = new int[jj][length];
            final int[] array = new int[jj * length];
            for (int ii = 0; ii < jj * length; ++ii) {
                array[ii] = target[ii % jj][ii / jj] = random.nextInt();
            }
            IntIla[] ilas = new IntIla[jj];
            for (int ii = 0; ii < jj; ++ii) {
                ilas[ii] = IntIlaFromArray.create(target[ii]);
            }
            IntIla targetIla = IntIlaFromArray.create(array);
            IntIla actualIla = IntIlaInterleave.create(ilas);
            final int epsilon = 0;
            IntIlaCheck.checkAll(
                    targetIla,
                    actualIla,
                    IlaTestDimensions.defaultOffsetLength(),
                    IlaTestDimensions.defaultMaxStride(),
                    epsilon);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
