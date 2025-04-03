package tfw.immutable.ila.byteila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.intila.IntIla;
import tfw.immutable.ila.intila.IntIlaFromArray;

final class ByteIlaFromCastIntIlaTest {
    @Test
    void argumentsTest() {
        final IntIla ila = IntIlaFromArray.create(new int[10]);

        assertThatThrownBy(() -> ByteIlaFromCastIntIla.create(null, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("intIla == null not allowed!");
        assertThatThrownBy(() -> ByteIlaFromCastIntIla.create(ila, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("bufferSize (=0) < 1 not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final int[] array = new int[length];
        final byte[] target = new byte[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = random.nextInt();
            target[ii] = (byte) array[ii];
        }
        IntIla ila = IntIlaFromArray.create(array);
        ByteIla targetIla = ByteIlaFromArray.create(target);
        ByteIla actualIla = ByteIlaFromCastIntIla.create(ila, 100);

        ByteIlaCheck.check(targetIla, actualIla);
    }
}
// AUTO GENERATED FROM TEMPLATE
