package tfw.immutable.ila.charila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class CharIlaInterleaveTest {
    @Test
    void testArguments() {
        final CharIla ila1 = CharIlaFromArray.create(new char[10]);
        final CharIla ila2 = CharIlaFromArray.create(new char[20]);
        final CharIla[] ilas1 = new CharIla[] {};
        final CharIla[] ilas2 = new CharIla[] {null, null};
        final CharIla[] ilas3 = new CharIla[] {null, ila1};
        final CharIla[] ilas4 = new CharIla[] {ila1, null};
        final CharIla[] ilas5 = new CharIla[] {ila1, ila1};
        final CharIla[] ilas6 = new CharIla[] {ila1, ila2};
        final char[] buffer = new char[10];

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
            CharIla[] ilas = new CharIla[jj];
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
