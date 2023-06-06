package tfw.immutable.ila.booleanila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;

/**
 *
 * @immutables.types=all
 */
public class BooleanIlaInterleaveTest extends TestCase {
    public void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        for (int jj = 2; jj < 6; ++jj) {
            final boolean[][] target = new boolean[jj][length];
            final boolean[] array = new boolean[jj * length];
            for (int ii = 0; ii < jj * length; ++ii) {
                array[ii] = target[ii % jj][ii / jj] = random.nextBoolean();
            }
            BooleanIla[] ilas = new BooleanIla[jj];
            for (int ii = 0; ii < jj; ++ii) {
                ilas[ii] = BooleanIlaFromArray.create(target[ii]);
            }
            BooleanIla targetIla = BooleanIlaFromArray.create(array);
            BooleanIla actualIla = BooleanIlaInterleave.create(ilas);
            final boolean epsilon = false;
            BooleanIlaCheck.checkAll(
                    targetIla,
                    actualIla,
                    IlaTestDimensions.defaultOffsetLength(),
                    IlaTestDimensions.defaultMaxStride(),
                    epsilon);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
