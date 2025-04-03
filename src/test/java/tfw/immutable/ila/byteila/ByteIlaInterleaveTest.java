package tfw.immutable.ila.byteila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.lang.reflect.Array;
import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class ByteIlaInterleaveTest {
    @Test
    void argumentsTest() {
        final ByteIla ila1 = ByteIlaFromArray.create(new byte[10]);
        final ByteIla ila2 = ByteIlaFromArray.create(new byte[20]);
        final ByteIla[] ilas1 = (ByteIla[]) Array.newInstance(ByteIla.class, 0);
        final ByteIla[] ilas2 = (ByteIla[]) Array.newInstance(ByteIla.class, 2);
        final ByteIla[] ilas3 = (ByteIla[]) Array.newInstance(ByteIla.class, 2);
        final ByteIla[] ilas4 = (ByteIla[]) Array.newInstance(ByteIla.class, 2);
        final ByteIla[] ilas5 = (ByteIla[]) Array.newInstance(ByteIla.class, 2);
        final ByteIla[] ilas6 = (ByteIla[]) Array.newInstance(ByteIla.class, 2);
        final byte[] buffer = new byte[10];

        ilas3[0] = null;
        ilas3[1] = ila1;
        ilas4[0] = ila1;
        ilas4[1] = null;
        ilas5[0] = ila1;
        ilas5[1] = ila1;
        ilas6[0] = ila1;
        ilas6[1] = ila2;

        assertThatThrownBy(() -> ByteIlaInterleave.create(null, buffer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilas == null not allowed!");
        assertThatThrownBy(() -> ByteIlaInterleave.create(ilas5, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("buffer == null not allowed!");
        assertThatThrownBy(() -> ByteIlaInterleave.create(ilas1, buffer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilas.length (=0) < 1 not allowed!");
        assertThatThrownBy(() -> ByteIlaInterleave.create(ilas2, buffer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilas[0] == null not allowed!");
        assertThatThrownBy(() -> ByteIlaInterleave.create(ilas3, buffer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilas[0] == null not allowed!");
        assertThatThrownBy(() -> ByteIlaInterleave.create(ilas4, buffer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilas[1] == null not allowed!");
        assertThatThrownBy(() -> ByteIlaInterleave.create(ilas6, buffer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ilas[0].length() (=20) != ilas[1].length() (=10) not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        for (int jj = 2; jj < 6; ++jj) {
            final byte[][] target = new byte[jj][length];
            final byte[] array = new byte[jj * length];
            for (int ii = 0; ii < jj * length; ++ii) {
                array[ii] = target[ii % jj][ii / jj] = (byte) random.nextInt();
            }
            ByteIla[] ilas = (ByteIla[]) Array.newInstance(ByteIla.class, jj);
            for (int ii = 0; ii < jj; ++ii) {
                ilas[ii] = ByteIlaFromArray.create(target[ii]);
            }
            ByteIla targetIla = ByteIlaFromArray.create(array);
            ByteIla actualIla = ByteIlaInterleave.create(ilas, new byte[1000]);

            ByteIlaCheck.check(targetIla, actualIla);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
