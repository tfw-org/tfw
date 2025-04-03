package tfw.immutable.ila.byteila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.shortila.ShortIla;
import tfw.immutable.ila.shortila.ShortIlaFromArray;

final class ByteIlaFromCastShortIlaTest {
    @Test
    void argumentsTest() {
        final ShortIla ila = ShortIlaFromArray.create(new short[10]);

        assertThatThrownBy(() -> ByteIlaFromCastShortIla.create(null, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("shortIla == null not allowed!");
        assertThatThrownBy(() -> ByteIlaFromCastShortIla.create(ila, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("bufferSize (=0) < 1 not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final short[] array = new short[length];
        final byte[] target = new byte[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = (short) random.nextInt();
            target[ii] = (byte) array[ii];
        }
        ShortIla ila = ShortIlaFromArray.create(array);
        ByteIla targetIla = ByteIlaFromArray.create(target);
        ByteIla actualIla = ByteIlaFromCastShortIla.create(ila, 100);

        ByteIlaCheck.check(targetIla, actualIla);
    }
}
// AUTO GENERATED FROM TEMPLATE
