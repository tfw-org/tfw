package tfw.immutable.ila.floatila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class FloatIlaInterleaveTest {
    @Test
    void testArguments() throws Exception {
        final FloatIla ila1 = FloatIlaFromArray.create(new float[10]);
        final FloatIla ila2 = FloatIlaFromArray.create(new float[20]);
        final FloatIla[] ilas1 = new FloatIla[] {};
        final FloatIla[] ilas2 = new FloatIla[] {null, null};
        final FloatIla[] ilas3 = new FloatIla[] {null, ila1};
        final FloatIla[] ilas4 = new FloatIla[] {ila1, null};
        final FloatIla[] ilas5 = new FloatIla[] {ila1, ila1};
        final FloatIla[] ilas6 = new FloatIla[] {ila1, ila2};
        final float[] buffer = new float[10];

        assertThrows(IllegalArgumentException.class, () -> FloatIlaInterleave.create(null, buffer));
        assertThrows(IllegalArgumentException.class, () -> FloatIlaInterleave.create(ilas5, null));
        assertThrows(IllegalArgumentException.class, () -> FloatIlaInterleave.create(ilas1, buffer));
        assertThrows(IllegalArgumentException.class, () -> FloatIlaInterleave.create(ilas2, buffer));
        assertThrows(IllegalArgumentException.class, () -> FloatIlaInterleave.create(ilas3, buffer));
        assertThrows(IllegalArgumentException.class, () -> FloatIlaInterleave.create(ilas4, buffer));
        assertThrows(IllegalArgumentException.class, () -> FloatIlaInterleave.create(ilas6, buffer));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        for (int jj = 2; jj < 6; ++jj) {
            final float[][] target = new float[jj][length];
            final float[] array = new float[jj * length];
            for (int ii = 0; ii < jj * length; ++ii) {
                array[ii] = target[ii % jj][ii / jj] = random.nextFloat();
            }
            FloatIla[] ilas = new FloatIla[jj];
            for (int ii = 0; ii < jj; ++ii) {
                ilas[ii] = FloatIlaFromArray.create(target[ii]);
            }
            FloatIla targetIla = FloatIlaFromArray.create(array);
            FloatIla actualIla = FloatIlaInterleave.create(ilas, new float[1000]);
            final float epsilon = 0.0f;
            FloatIlaCheck.checkAll(
                    targetIla,
                    actualIla,
                    IlaTestDimensions.defaultOffsetLength(),
                    IlaTestDimensions.defaultMaxStride(),
                    epsilon);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
