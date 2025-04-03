package tfw.immutable.ila.doubleila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.lang.reflect.Array;
import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class DoubleIlaInterleaveTest {
    @Test
    void argumentsTest() {
        final DoubleIla ila1 = DoubleIlaFromArray.create(new double[10]);
        final DoubleIla ila2 = DoubleIlaFromArray.create(new double[20]);
        final DoubleIla[] ilas1 = (DoubleIla[]) Array.newInstance(DoubleIla.class, 0);
        final DoubleIla[] ilas2 = (DoubleIla[]) Array.newInstance(DoubleIla.class, 2);
        final DoubleIla[] ilas3 = (DoubleIla[]) Array.newInstance(DoubleIla.class, 2);
        final DoubleIla[] ilas4 = (DoubleIla[]) Array.newInstance(DoubleIla.class, 2);
        final DoubleIla[] ilas5 = (DoubleIla[]) Array.newInstance(DoubleIla.class, 2);
        final DoubleIla[] ilas6 = (DoubleIla[]) Array.newInstance(DoubleIla.class, 2);
        final double[] buffer = new double[10];

        ilas3[0] = null;
        ilas3[1] = ila1;
        ilas4[0] = ila1;
        ilas4[1] = null;
        ilas5[0] = ila1;
        ilas5[1] = ila1;
        ilas6[0] = ila1;
        ilas6[1] = ila2;

        assertThatThrownBy(() -> DoubleIlaInterleave.create(null, buffer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilas == null not allowed!");
        assertThatThrownBy(() -> DoubleIlaInterleave.create(ilas5, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("buffer == null not allowed!");
        assertThatThrownBy(() -> DoubleIlaInterleave.create(ilas1, buffer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilas.length (=0) < 1 not allowed!");
        assertThatThrownBy(() -> DoubleIlaInterleave.create(ilas2, buffer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilas[0] == null not allowed!");
        assertThatThrownBy(() -> DoubleIlaInterleave.create(ilas3, buffer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilas[0] == null not allowed!");
        assertThatThrownBy(() -> DoubleIlaInterleave.create(ilas4, buffer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilas[1] == null not allowed!");
        assertThatThrownBy(() -> DoubleIlaInterleave.create(ilas6, buffer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilas[0].length() (=20) != ilas[1].length() (=10) not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        for (int jj = 2; jj < 6; ++jj) {
            final double[][] target = new double[jj][length];
            final double[] array = new double[jj * length];
            for (int ii = 0; ii < jj * length; ++ii) {
                array[ii] = target[ii % jj][ii / jj] = random.nextDouble();
            }
            DoubleIla[] ilas = (DoubleIla[]) Array.newInstance(DoubleIla.class, jj);
            for (int ii = 0; ii < jj; ++ii) {
                ilas[ii] = DoubleIlaFromArray.create(target[ii]);
            }
            DoubleIla targetIla = DoubleIlaFromArray.create(array);
            DoubleIla actualIla = DoubleIlaInterleave.create(ilas, new double[1000]);

            DoubleIlaCheck.check(targetIla, actualIla);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
