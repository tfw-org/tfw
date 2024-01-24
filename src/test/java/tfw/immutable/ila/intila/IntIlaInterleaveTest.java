package tfw.immutable.ila.intila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class IntIlaInterleaveTest {
    @Test
    void testArguments() throws Exception {
        final IntIla ila1 = IntIlaFromArray.create(new int[10]);
        final IntIla ila2 = IntIlaFromArray.create(new int[20]);
        final IntIla[] ilas1 = new IntIla[] {};
        final IntIla[] ilas2 = new IntIla[] {null, null};
        final IntIla[] ilas3 = new IntIla[] {null, ila1};
        final IntIla[] ilas4 = new IntIla[] {ila1, null};
        final IntIla[] ilas5 = new IntIla[] {ila1, ila1};
        final IntIla[] ilas6 = new IntIla[] {ila1, ila2};
        final int[] buffer = new int[10];

        assertThrows(IllegalArgumentException.class, () -> IntIlaInterleave.create(null, buffer));
        assertThrows(IllegalArgumentException.class, () -> IntIlaInterleave.create(ilas5, null));
        assertThrows(IllegalArgumentException.class, () -> IntIlaInterleave.create(ilas1, buffer));
        assertThrows(IllegalArgumentException.class, () -> IntIlaInterleave.create(ilas2, buffer));
        assertThrows(IllegalArgumentException.class, () -> IntIlaInterleave.create(ilas3, buffer));
        assertThrows(IllegalArgumentException.class, () -> IntIlaInterleave.create(ilas4, buffer));
        assertThrows(IllegalArgumentException.class, () -> IntIlaInterleave.create(ilas6, buffer));
    }

    @Test
    void testAll() throws Exception {
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
            IntIla actualIla = IntIlaInterleave.create(ilas, new int[1000]);

            IntIlaCheck.check(targetIla, actualIla);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
