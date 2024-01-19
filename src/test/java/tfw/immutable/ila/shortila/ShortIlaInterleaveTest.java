package tfw.immutable.ila.shortila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class ShortIlaInterleaveTest {
    @Test
    void testArguments() throws Exception {
        final ShortIla ila1 = ShortIlaFromArray.create(new short[10]);
        final ShortIla ila2 = ShortIlaFromArray.create(new short[20]);
        final ShortIla[] ilas1 = new ShortIla[] {};
        final ShortIla[] ilas2 = new ShortIla[] {null, null};
        final ShortIla[] ilas3 = new ShortIla[] {null, ila1};
        final ShortIla[] ilas4 = new ShortIla[] {ila1, null};
        final ShortIla[] ilas5 = new ShortIla[] {ila1, ila1};
        final ShortIla[] ilas6 = new ShortIla[] {ila1, ila2};
        final short[] buffer = new short[10];

        assertThrows(IllegalArgumentException.class, () -> ShortIlaInterleave.create(null, buffer));
        assertThrows(IllegalArgumentException.class, () -> ShortIlaInterleave.create(ilas5, null));
        assertThrows(IllegalArgumentException.class, () -> ShortIlaInterleave.create(ilas1, buffer));
        assertThrows(IllegalArgumentException.class, () -> ShortIlaInterleave.create(ilas2, buffer));
        assertThrows(IllegalArgumentException.class, () -> ShortIlaInterleave.create(ilas3, buffer));
        assertThrows(IllegalArgumentException.class, () -> ShortIlaInterleave.create(ilas4, buffer));
        assertThrows(IllegalArgumentException.class, () -> ShortIlaInterleave.create(ilas6, buffer));
    }

    @Test
    void testAll() throws Exception {
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
            ShortIla actualIla = ShortIlaInterleave.create(ilas, new short[1000]);
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
