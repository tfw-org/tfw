package tfw.immutable.ila.shortila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class ShortIlaMutateTest {
    @Test
    void argumentsTest() throws Exception {
        final Random random = new Random(0);
        final ShortIla ila = ShortIlaFromArray.create(new short[10]);
        final long ilaLength = ila.length();
        final short value = (short) random.nextInt();

        assertThatThrownBy(() -> ShortIlaMutate.create(null, 0, value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ila == null not allowed!");
        assertThatThrownBy(() -> ShortIlaMutate.create(ila, -1, value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("index (=-1) < 0 not allowed!");
        assertThatThrownBy(() -> ShortIlaMutate.create(ila, ilaLength, value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("index (=10) >= ila.length() (=10) not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final short[] array = new short[length];
        final short[] target = new short[length];
        for (int index = 0; index < length; ++index) {
            for (int ii = 0; ii < array.length; ++ii) {
                array[ii] = target[ii] = (short) random.nextInt();
            }
            final short value = (short) random.nextInt();
            target[index] = value;
            ShortIla origIla = ShortIlaFromArray.create(array);
            ShortIla targetIla = ShortIlaFromArray.create(target);
            ShortIla actualIla = ShortIlaMutate.create(origIla, index, value);

            ShortIlaCheck.check(targetIla, actualIla);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
