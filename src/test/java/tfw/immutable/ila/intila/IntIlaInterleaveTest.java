package tfw.immutable.ila.intila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.Array;
import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class IntIlaInterleaveTest {
    @Test
    void testArguments() {
        final IntIla ila1 = IntIlaFromArray.create(new int[10]);
        final IntIla ila2 = IntIlaFromArray.create(new int[20]);
        final IntIla[] ilas1 = (IntIla[]) Array.newInstance(IntIla.class, 0);
        final IntIla[] ilas2 = (IntIla[]) Array.newInstance(IntIla.class, 2);
        final IntIla[] ilas3 = (IntIla[]) Array.newInstance(IntIla.class, 2);
        final IntIla[] ilas4 = (IntIla[]) Array.newInstance(IntIla.class, 2);
        final IntIla[] ilas5 = (IntIla[]) Array.newInstance(IntIla.class, 2);
        final IntIla[] ilas6 = (IntIla[]) Array.newInstance(IntIla.class, 2);
        final int[] buffer = new int[10];

        ilas3[0] = null;
        ilas3[1] = ila1;
        ilas4[0] = ila1;
        ilas4[1] = null;
        ilas5[0] = ila1;
        ilas5[1] = ila1;
        ilas6[0] = ila1;
        ilas6[1] = ila2;

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
            IntIla[] ilas = (IntIla[]) Array.newInstance(IntIla.class, jj);
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
