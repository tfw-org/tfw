package tfw.immutable.ila.objectila;

import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class ObjectIlaInterleaveTest {
    @Test
    @SuppressWarnings("unchecked")
    void testAll() throws Exception {
        final int length = IlaTestDimensions.defaultIlaLength();
        for (int jj = 2; jj < 6; ++jj) {
            final Object[][] target = new Object[jj][length];
            final Object[] array = new Object[jj * length];
            for (int ii = 0; ii < jj * length; ++ii) {
                array[ii] = target[ii % jj][ii / jj] = new Object();
            }
            ObjectIla<Object>[] ilas = (ObjectIla<Object>[]) new ObjectIla[jj];
            for (int ii = 0; ii < jj; ++ii) {
                ilas[ii] = ObjectIlaFromArray.create(target[ii]);
            }
            ObjectIla<Object> targetIla = ObjectIlaFromArray.create(array);
            ObjectIla<Object> actualIla = ObjectIlaInterleave.create(ilas, new Object[1000]);
            final Object epsilon = Object.class;
            ObjectIlaCheck.checkAll(
                    targetIla,
                    actualIla,
                    IlaTestDimensions.defaultOffsetLength(),
                    IlaTestDimensions.defaultMaxStride(),
                    epsilon);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
