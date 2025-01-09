package tfw.immutable.ila.byteila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaFromArray;

final class ByteIlaFromCastLongIlaTest {
    @Test
    void argumentsTest() {
        final LongIla ila = LongIlaFromArray.create(new long[10]);

        assertThatThrownBy(() -> ByteIlaFromCastLongIla.create(null, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("longIla == null not allowed!");
        assertThatThrownBy(() -> ByteIlaFromCastLongIla.create(ila, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("bufferSize (=0) < 1 not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final long[] array = new long[length];
        final byte[] target = new byte[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = random.nextLong();
            target[ii] = (byte) array[ii];
        }
        LongIla ila = LongIlaFromArray.create(array);
        ByteIla targetIla = ByteIlaFromArray.create(target);
        ByteIla actualIla = ByteIlaFromCastLongIla.create(ila, 100);

        ByteIlaCheck.check(targetIla, actualIla);
    }
}
// AUTO GENERATED FROM TEMPLATE
