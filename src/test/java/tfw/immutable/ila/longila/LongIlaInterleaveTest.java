package tfw.immutable.ila.longila;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class LongIlaInterleaveTest {
    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        for (int jj = 2; jj < 6; ++jj) {
            final long[][] target = new long[jj][length];
            final long[] array = new long[jj * length];
            for (int ii = 0; ii < jj * length; ++ii) {
                array[ii] = target[ii % jj][ii / jj] = random.nextLong();
            }
            LongIla[] ilas = new LongIla[jj];
            for (int ii = 0; ii < jj; ++ii) {
                ilas[ii] = LongIlaFromArray.create(target[ii]);
            }
            LongIla targetIla = LongIlaFromArray.create(array);
            LongIla actualIla = LongIlaInterleave.create(ilas);
            final long epsilon = 0L;
            LongIlaCheck.checkAll(
                    targetIla,
                    actualIla,
                    IlaTestDimensions.defaultOffsetLength(),
                    IlaTestDimensions.defaultMaxStride(),
                    epsilon);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
