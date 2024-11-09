package tfw.immutable.ila.byteila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.Array;
import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class ByteIlaInterleaveTest {
    @Test
    void testArguments() {
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

        assertThrows(IllegalArgumentException.class, () -> ByteIlaInterleave.create(null, buffer));
        assertThrows(IllegalArgumentException.class, () -> ByteIlaInterleave.create(ilas5, null));
        assertThrows(IllegalArgumentException.class, () -> ByteIlaInterleave.create(ilas1, buffer));
        assertThrows(IllegalArgumentException.class, () -> ByteIlaInterleave.create(ilas2, buffer));
        assertThrows(IllegalArgumentException.class, () -> ByteIlaInterleave.create(ilas3, buffer));
        assertThrows(IllegalArgumentException.class, () -> ByteIlaInterleave.create(ilas4, buffer));
        assertThrows(IllegalArgumentException.class, () -> ByteIlaInterleave.create(ilas6, buffer));
    }

    @Test
    void testAll() throws Exception {
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
