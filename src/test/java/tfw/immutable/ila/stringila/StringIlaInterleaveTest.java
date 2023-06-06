package tfw.immutable.ila.stringila;

import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;

/**
 *
 * @immutables.types=all
 */
public class StringIlaInterleaveTest extends TestCase {
    public void testAll() throws Exception {

        final int length = IlaTestDimensions.defaultIlaLength();
        for (int jj = 2; jj < 6; ++jj) {
            final String[][] target = new String[jj][length];
            final String[] array = new String[jj * length];
            for (int ii = 0; ii < jj * length; ++ii) {
                array[ii] = target[ii % jj][ii / jj] = new String();
            }
            StringIla[] ilas = new StringIla[jj];
            for (int ii = 0; ii < jj; ++ii) {
                ilas[ii] = StringIlaFromArray.create(target[ii]);
            }
            StringIla targetIla = StringIlaFromArray.create(array);
            StringIla actualIla = StringIlaInterleave.create(ilas);
            final String epsilon = "";
            StringIlaCheck.checkAll(
                    targetIla,
                    actualIla,
                    IlaTestDimensions.defaultOffsetLength(),
                    IlaTestDimensions.defaultMaxStride(),
                    epsilon);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
