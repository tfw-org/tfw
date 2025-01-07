package tfw.immutable.ila.byteila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class ByteIlaBoundTest {
    @Test
    void argumentsTest() {
        final byte low = (byte) 5;
        final byte high = (byte) 10;
        final ByteIla ila = ByteIlaFromArray.create(new byte[10]);

        assertThatThrownBy(() -> ByteIlaBound.create(null, low, high))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ila == null not allowed!");
        assertThatThrownBy(() -> ByteIlaBound.create(ila, high, low))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("minimum (=" + high + ") > maximum (=" + low + ") not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final byte[] array = new byte[length];
        final byte[] target = new byte[length];
        byte minimum = (byte) random.nextInt();
        byte maximum = (byte) random.nextInt();
        if (minimum > maximum) {
            byte tmp = minimum;
            minimum = maximum;
            maximum = tmp;
        }
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = (byte) random.nextInt();
            target[ii] = array[ii];
            if (target[ii] < minimum) {
                target[ii] = minimum;
            } else if (target[ii] > maximum) {
                target[ii] = maximum;
            }
        }
        ByteIla ila = ByteIlaFromArray.create(array);
        ByteIla targetIla = ByteIlaFromArray.create(target);
        ByteIla actualIla = ByteIlaBound.create(ila, minimum, maximum);

        ByteIlaCheck.check(targetIla, actualIla);
    }
}
// AUTO GENERATED FROM TEMPLATE
