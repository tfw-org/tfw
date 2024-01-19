package tfw.immutable.ila.longila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class LongIlaInterleaveTest {
    @Test
    void testArguments() throws Exception {
        final LongIla ila1 = LongIlaFromArray.create(new long[10]);
        final LongIla ila2 = LongIlaFromArray.create(new long[20]);
        final LongIla[] ilas1 = new LongIla[] {};
        final LongIla[] ilas2 = new LongIla[] {null, null};
        final LongIla[] ilas3 = new LongIla[] {null, ila1};
        final LongIla[] ilas4 = new LongIla[] {ila1, null};
        final LongIla[] ilas5 = new LongIla[] {ila1, ila1};
        final LongIla[] ilas6 = new LongIla[] {ila1, ila2};
        final long[] buffer = new long[10];

        assertThrows(IllegalArgumentException.class, () -> LongIlaInterleave.create(null, buffer));
        assertThrows(IllegalArgumentException.class, () -> LongIlaInterleave.create(ilas5, null));
        assertThrows(IllegalArgumentException.class, () -> LongIlaInterleave.create(ilas1, buffer));
        assertThrows(IllegalArgumentException.class, () -> LongIlaInterleave.create(ilas2, buffer));
        assertThrows(IllegalArgumentException.class, () -> LongIlaInterleave.create(ilas3, buffer));
        assertThrows(IllegalArgumentException.class, () -> LongIlaInterleave.create(ilas4, buffer));
        assertThrows(IllegalArgumentException.class, () -> LongIlaInterleave.create(ilas6, buffer));
    }

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
            LongIla actualIla = LongIlaInterleave.create(ilas, new long[1000]);
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
