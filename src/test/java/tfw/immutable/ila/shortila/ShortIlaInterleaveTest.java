package tfw.immutable.ila.shortila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;

/**
 *
 * @immutables.types=all
 */
public class ShortIlaInterleaveTest extends TestCase {
    public void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        for (int jj = 2; jj < 6; ++jj) {
            final short[][] target = new short[jj][length];
            final short[] array = new short[jj * length];
            for (int ii = 0; ii < jj * length; ++ii) {
                array[ii] = target[ii % jj][ii / jj] = (short) random.nextInt();
            }
            ShortIla[] ilas = new ShortIla[jj];
            for (int ii = 0; ii < jj; ++ii) {
                ilas[ii] = ShortIlaFromArray.create(target[ii]);
            }
            ShortIla targetIla = ShortIlaFromArray.create(array);
            ShortIla actualIla = ShortIlaInterleave.create(ilas);
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
