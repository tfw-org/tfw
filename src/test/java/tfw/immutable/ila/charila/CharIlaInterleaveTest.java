package tfw.immutable.ila.charila;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

/**
 *
 * @immutables.types=all
 */
class CharIlaInterleaveTest {
    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        for (int jj = 2; jj < 6; ++jj) {
            final char[][] target = new char[jj][length];
            final char[] array = new char[jj * length];
            for (int ii = 0; ii < jj * length; ++ii) {
                array[ii] = target[ii % jj][ii / jj] = (char) random.nextInt();
            }
            CharIla[] ilas = new CharIla[jj];
            for (int ii = 0; ii < jj; ++ii) {
                ilas[ii] = CharIlaFromArray.create(target[ii]);
            }
            CharIla targetIla = CharIlaFromArray.create(array);
            CharIla actualIla = CharIlaInterleave.create(ilas);
            final char epsilon = (char) 0;
            CharIlaCheck.checkAll(
                    targetIla,
                    actualIla,
                    IlaTestDimensions.defaultOffsetLength(),
                    IlaTestDimensions.defaultMaxStride(),
                    epsilon);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
