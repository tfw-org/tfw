package tfw.immutable.ila.doubleila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class DoubleIlaInterleaveTest {
    @Test
    void testArguments() throws Exception {
        final DoubleIla ila1 = DoubleIlaFromArray.create(new double[10]);
        final DoubleIla ila2 = DoubleIlaFromArray.create(new double[20]);
        final DoubleIla[] ilas1 = new DoubleIla[] {};
        final DoubleIla[] ilas2 = new DoubleIla[] {null, null};
        final DoubleIla[] ilas3 = new DoubleIla[] {null, ila1};
        final DoubleIla[] ilas4 = new DoubleIla[] {ila1, null};
        final DoubleIla[] ilas5 = new DoubleIla[] {ila1, ila1};
        final DoubleIla[] ilas6 = new DoubleIla[] {ila1, ila2};
        final double[] buffer = new double[10];

        assertThrows(IllegalArgumentException.class, () -> DoubleIlaInterleave.create(null, buffer));
        assertThrows(IllegalArgumentException.class, () -> DoubleIlaInterleave.create(ilas5, null));
        assertThrows(IllegalArgumentException.class, () -> DoubleIlaInterleave.create(ilas1, buffer));
        assertThrows(IllegalArgumentException.class, () -> DoubleIlaInterleave.create(ilas2, buffer));
        assertThrows(IllegalArgumentException.class, () -> DoubleIlaInterleave.create(ilas3, buffer));
        assertThrows(IllegalArgumentException.class, () -> DoubleIlaInterleave.create(ilas4, buffer));
        assertThrows(IllegalArgumentException.class, () -> DoubleIlaInterleave.create(ilas6, buffer));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        for (int jj = 2; jj < 6; ++jj) {
            final double[][] target = new double[jj][length];
            final double[] array = new double[jj * length];
            for (int ii = 0; ii < jj * length; ++ii) {
                array[ii] = target[ii % jj][ii / jj] = random.nextDouble();
            }
            DoubleIla[] ilas = new DoubleIla[jj];
            for (int ii = 0; ii < jj; ++ii) {
                ilas[ii] = DoubleIlaFromArray.create(target[ii]);
            }
            DoubleIla targetIla = DoubleIlaFromArray.create(array);
            DoubleIla actualIla = DoubleIlaInterleave.create(ilas, new double[1000]);
            final double epsilon = 0.0;
            DoubleIlaCheck.checkAll(
                    targetIla,
                    actualIla,
                    IlaTestDimensions.defaultOffsetLength(),
                    IlaTestDimensions.defaultMaxStride(),
                    epsilon);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
