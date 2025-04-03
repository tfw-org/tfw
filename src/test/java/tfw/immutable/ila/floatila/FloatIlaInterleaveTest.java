package tfw.immutable.ila.floatila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.lang.reflect.Array;
import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class FloatIlaInterleaveTest {
    @Test
    void argumentsTest() {
        final FloatIla ila1 = FloatIlaFromArray.create(new float[10]);
        final FloatIla ila2 = FloatIlaFromArray.create(new float[20]);
        final FloatIla[] ilas1 = (FloatIla[]) Array.newInstance(FloatIla.class, 0);
        final FloatIla[] ilas2 = (FloatIla[]) Array.newInstance(FloatIla.class, 2);
        final FloatIla[] ilas3 = (FloatIla[]) Array.newInstance(FloatIla.class, 2);
        final FloatIla[] ilas4 = (FloatIla[]) Array.newInstance(FloatIla.class, 2);
        final FloatIla[] ilas5 = (FloatIla[]) Array.newInstance(FloatIla.class, 2);
        final FloatIla[] ilas6 = (FloatIla[]) Array.newInstance(FloatIla.class, 2);
        final float[] buffer = new float[10];

        ilas3[0] = null;
        ilas3[1] = ila1;
        ilas4[0] = ila1;
        ilas4[1] = null;
        ilas5[0] = ila1;
        ilas5[1] = ila1;
        ilas6[0] = ila1;
        ilas6[1] = ila2;

        assertThatThrownBy(() -> FloatIlaInterleave.create(null, buffer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilas == null not allowed!");
        assertThatThrownBy(() -> FloatIlaInterleave.create(ilas5, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("buffer == null not allowed!");
        assertThatThrownBy(() -> FloatIlaInterleave.create(ilas1, buffer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilas.length (=0) < 1 not allowed!");
        assertThatThrownBy(() -> FloatIlaInterleave.create(ilas2, buffer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilas[0] == null not allowed!");
        assertThatThrownBy(() -> FloatIlaInterleave.create(ilas3, buffer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilas[0] == null not allowed!");
        assertThatThrownBy(() -> FloatIlaInterleave.create(ilas4, buffer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilas[1] == null not allowed!");
        assertThatThrownBy(() -> FloatIlaInterleave.create(ilas6, buffer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilas[0].length() (=20) != ilas[1].length() (=10) not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        for (int jj = 2; jj < 6; ++jj) {
            final float[][] target = new float[jj][length];
            final float[] array = new float[jj * length];
            for (int ii = 0; ii < jj * length; ++ii) {
                array[ii] = target[ii % jj][ii / jj] = random.nextFloat();
            }
            FloatIla[] ilas = (FloatIla[]) Array.newInstance(FloatIla.class, jj);
            for (int ii = 0; ii < jj; ++ii) {
                ilas[ii] = FloatIlaFromArray.create(target[ii]);
            }
            FloatIla targetIla = FloatIlaFromArray.create(array);
            FloatIla actualIla = FloatIlaInterleave.create(ilas, new float[1000]);

            FloatIlaCheck.check(targetIla, actualIla);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
