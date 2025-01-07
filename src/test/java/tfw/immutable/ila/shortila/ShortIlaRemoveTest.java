package tfw.immutable.ila.shortila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class ShortIlaRemoveTest {
    @Test
    void argumentsTest() throws Exception {
        final ShortIla ila = ShortIlaFromArray.create(new short[10]);
        final long ilaLength = ila.length();

        assertThatThrownBy(() -> ShortIlaRemove.create(null, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ila == null not allowed!");
        assertThatThrownBy(() -> ShortIlaRemove.create(ila, -1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("index (=-1) < 0 not allowed!");
        assertThatThrownBy(() -> ShortIlaRemove.create(ila, ilaLength))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("index (=10) >= ila.length() (=10) not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final short[] array = new short[length];
        final short[] target = new short[length - 1];
        for (int index = 0; index < length; ++index) {
            int targetii = 0;
            for (int ii = 0; ii < array.length; ++ii) {
                array[ii] = (short) random.nextInt();
                if (ii != index) {
                    target[targetii++] = array[ii];
                }
            }
            ShortIla origIla = ShortIlaFromArray.create(array);
            ShortIla targetIla = ShortIlaFromArray.create(target);
            ShortIla actualIla = ShortIlaRemove.create(origIla, index);

            ShortIlaCheck.check(targetIla, actualIla);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
