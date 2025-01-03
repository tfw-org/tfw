package tfw.immutable.ila.byteila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class ByteIlaFillTest {
    @Test
    void argumentsTest() {
        final Random random = new Random(0);
        final byte value = (byte) random.nextInt();

        assertThatThrownBy(() -> ByteIlaFill.create(value, -1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("length (=-1) < 0 not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final byte value = (byte) random.nextInt();
        final int length = IlaTestDimensions.defaultIlaLength();
        final byte[] array = new byte[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = value;
        }
        ByteIla targetIla = ByteIlaFromArray.create(array);
        ByteIla actualIla = ByteIlaFill.create(value, length);

        ByteIlaCheck.check(targetIla, actualIla);
    }
}
// AUTO GENERATED FROM TEMPLATE
