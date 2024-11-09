package tfw.immutable.ila.charila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.Array;
import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class CharIlaInterleaveTest {
    @Test
    void testArguments() {
        final CharIla ila1 = CharIlaFromArray.create(new char[10]);
        final CharIla ila2 = CharIlaFromArray.create(new char[20]);
        final CharIla[] ilas1 = (CharIla[]) Array.newInstance(CharIla.class, 0);
        final CharIla[] ilas2 = (CharIla[]) Array.newInstance(CharIla.class, 2);
        final CharIla[] ilas3 = (CharIla[]) Array.newInstance(CharIla.class, 2);
        final CharIla[] ilas4 = (CharIla[]) Array.newInstance(CharIla.class, 2);
        final CharIla[] ilas5 = (CharIla[]) Array.newInstance(CharIla.class, 2);
        final CharIla[] ilas6 = (CharIla[]) Array.newInstance(CharIla.class, 2);
        final char[] buffer = new char[10];

        ilas3[0] = null;
        ilas3[1] = ila1;
        ilas4[0] = ila1;
        ilas4[1] = null;
        ilas5[0] = ila1;
        ilas5[1] = ila1;
        ilas6[0] = ila1;
        ilas6[1] = ila2;

        assertThrows(IllegalArgumentException.class, () -> CharIlaInterleave.create(null, buffer));
        assertThrows(IllegalArgumentException.class, () -> CharIlaInterleave.create(ilas5, null));
        assertThrows(IllegalArgumentException.class, () -> CharIlaInterleave.create(ilas1, buffer));
        assertThrows(IllegalArgumentException.class, () -> CharIlaInterleave.create(ilas2, buffer));
        assertThrows(IllegalArgumentException.class, () -> CharIlaInterleave.create(ilas3, buffer));
        assertThrows(IllegalArgumentException.class, () -> CharIlaInterleave.create(ilas4, buffer));
        assertThrows(IllegalArgumentException.class, () -> CharIlaInterleave.create(ilas6, buffer));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        for (int jj = 2; jj < 6; ++jj) {
            final char[][] target = new char[jj][length];
            final char[] array = new char[jj * length];
            for (int ii = 0; ii < jj * length; ++ii) {
                array[ii] = target[ii % jj][ii / jj] = (char) random.nextInt();
            }
            CharIla[] ilas = (CharIla[]) Array.newInstance(CharIla.class, jj);
            for (int ii = 0; ii < jj; ++ii) {
                ilas[ii] = CharIlaFromArray.create(target[ii]);
            }
            CharIla targetIla = CharIlaFromArray.create(array);
            CharIla actualIla = CharIlaInterleave.create(ilas, new char[1000]);

            CharIlaCheck.check(targetIla, actualIla);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
