package tfw.immutable.ila.byteila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;

/**
 *
 * @immutables.types=all
 */
public class ByteIlaInterleaveTest extends TestCase {
    public void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        for (int jj = 2; jj < 6; ++jj) {
            final byte[][] target = new byte[jj][length];
            final byte[] array = new byte[jj * length];
            for (int ii = 0; ii < jj * length; ++ii) {
                array[ii] = target[ii % jj][ii / jj] = (byte) random.nextInt();
            }
            ByteIla[] ilas = new ByteIla[jj];
            for (int ii = 0; ii < jj; ++ii) {
                ilas[ii] = ByteIlaFromArray.create(target[ii]);
            }
            ByteIla targetIla = ByteIlaFromArray.create(array);
            ByteIla actualIla = ByteIlaInterleave.create(ilas);
            final byte epsilon = (byte) 0;
            ByteIlaCheck.checkAll(
                    targetIla,
                    actualIla,
                    IlaTestDimensions.defaultOffsetLength(),
                    IlaTestDimensions.defaultMaxStride(),
                    epsilon);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
