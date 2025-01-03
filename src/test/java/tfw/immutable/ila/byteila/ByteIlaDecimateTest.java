package tfw.immutable.ila.byteila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class ByteIlaDecimateTest {
    @Test
    void argumentsTest() {
        final ByteIla ila = ByteIlaFromArray.create(new byte[10]);
        final byte[] buffer = new byte[10];

        assertThatThrownBy(() -> ByteIlaDecimate.create(null, 2, buffer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ila == null not allowed!");
        assertThatThrownBy(() -> ByteIlaDecimate.create(ila, 2, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("buffer == null not allowed!");
        assertThatThrownBy(() -> ByteIlaDecimate.create(ila, 1, buffer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("factor (=1) < 2 not allowed!");
        assertThatThrownBy(() -> ByteIlaDecimate.create(ila, 2, new byte[0]))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("buffer.length (=0) < 1 not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final byte[] array = new byte[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = (byte) random.nextInt();
        }
        ByteIla ila = ByteIlaFromArray.create(array);
        for (int factor = 2; factor <= length; ++factor) {
            final int targetLength = (length + factor - 1) / factor;
            final byte[] target = new byte[targetLength];
            for (int ii = 0; ii < target.length; ++ii) {
                target[ii] = array[ii * factor];
            }
            ByteIla targetIla = ByteIlaFromArray.create(target);
            ByteIla actualIla = ByteIlaDecimate.create(ila, factor, new byte[100]);

            ByteIlaCheck.check(targetIla, actualIla);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
