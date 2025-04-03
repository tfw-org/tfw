package tfw.immutable.ila.byteila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.charila.CharIla;
import tfw.immutable.ila.charila.CharIlaFromArray;

final class ByteIlaFromCastCharIlaTest {
    @Test
    void argumentsTest() {
        final CharIla ila = CharIlaFromArray.create(new char[10]);

        assertThatThrownBy(() -> ByteIlaFromCastCharIla.create(null, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("charIla == null not allowed!");
        assertThatThrownBy(() -> ByteIlaFromCastCharIla.create(ila, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("bufferSize (=0) < 1 not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final char[] array = new char[length];
        final byte[] target = new byte[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = (char) random.nextInt();
            target[ii] = (byte) array[ii];
        }
        CharIla ila = CharIlaFromArray.create(array);
        ByteIla targetIla = ByteIlaFromArray.create(target);
        ByteIla actualIla = ByteIlaFromCastCharIla.create(ila, 100);

        ByteIlaCheck.check(targetIla, actualIla);
    }
}
// AUTO GENERATED FROM TEMPLATE
