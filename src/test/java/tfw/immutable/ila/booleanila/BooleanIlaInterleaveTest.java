package tfw.immutable.ila.booleanila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class BooleanIlaInterleaveTest {
    @Test
    void testArguments() throws Exception {
        final BooleanIla ila1 = BooleanIlaFromArray.create(new boolean[10]);
        final BooleanIla ila2 = BooleanIlaFromArray.create(new boolean[20]);
        final BooleanIla[] ilas1 = new BooleanIla[] {};
        final BooleanIla[] ilas2 = new BooleanIla[] {null, null};
        final BooleanIla[] ilas3 = new BooleanIla[] {null, ila1};
        final BooleanIla[] ilas4 = new BooleanIla[] {ila1, null};
        final BooleanIla[] ilas5 = new BooleanIla[] {ila1, ila1};
        final BooleanIla[] ilas6 = new BooleanIla[] {ila1, ila2};
        final boolean[] buffer = new boolean[10];

        assertThrows(IllegalArgumentException.class, () -> BooleanIlaInterleave.create(null, buffer));
        assertThrows(IllegalArgumentException.class, () -> BooleanIlaInterleave.create(ilas5, null));
        assertThrows(IllegalArgumentException.class, () -> BooleanIlaInterleave.create(ilas1, buffer));
        assertThrows(IllegalArgumentException.class, () -> BooleanIlaInterleave.create(ilas2, buffer));
        assertThrows(IllegalArgumentException.class, () -> BooleanIlaInterleave.create(ilas3, buffer));
        assertThrows(IllegalArgumentException.class, () -> BooleanIlaInterleave.create(ilas4, buffer));
        assertThrows(IllegalArgumentException.class, () -> BooleanIlaInterleave.create(ilas6, buffer));
    }

    @Test
    void testAll() throws Exception {
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
            BooleanIla actualIla = BooleanIlaInterleave.create(ilas, new boolean[1000]);

            BooleanIlaCheck.check(targetIla, actualIla);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
