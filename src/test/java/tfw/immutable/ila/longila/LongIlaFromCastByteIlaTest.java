package tfw.immutable.ila.longila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaFromArray;

final class LongIlaFromCastByteIlaTest {
    @Test
    void argumentsTest() {
        final ByteIla ila = ByteIlaFromArray.create(new byte[10]);

        assertThatThrownBy(() -> LongIlaFromCastByteIla.create(null, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("byteIla == null not allowed!");
        assertThatThrownBy(() -> LongIlaFromCastByteIla.create(ila, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("bufferSize (=0) < 1 not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final byte[] array = new byte[length];
        final long[] target = new long[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = (byte) random.nextInt();
            target[ii] = (long) array[ii];
        }
        ByteIla ila = ByteIlaFromArray.create(array);
        LongIla targetIla = LongIlaFromArray.create(target);
        LongIla actualIla = LongIlaFromCastByteIla.create(ila, 100);

        LongIlaCheck.check(targetIla, actualIla);
    }
}
// AUTO GENERATED FROM TEMPLATE
