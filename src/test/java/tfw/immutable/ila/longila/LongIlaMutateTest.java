package tfw.immutable.ila.longila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class LongIlaMutateTest {
    @Test
    void argumentsTest() throws Exception {
        final Random random = new Random(0);
        final LongIla ila = LongIlaFromArray.create(new long[10]);
        final long ilaLength = ila.length();
        final long value = random.nextLong();

        assertThatThrownBy(() -> LongIlaMutate.create(null, 0, value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ila == null not allowed!");
        assertThatThrownBy(() -> LongIlaMutate.create(ila, -1, value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("index (=-1) < 0 not allowed!");
        assertThatThrownBy(() -> LongIlaMutate.create(ila, ilaLength, value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("index (=10) >= ila.length() (=10) not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final long[] array = new long[length];
        final long[] target = new long[length];
        for (int index = 0; index < length; ++index) {
            for (int ii = 0; ii < array.length; ++ii) {
                array[ii] = target[ii] = random.nextLong();
            }
            final long value = random.nextLong();
            target[index] = value;
            LongIla origIla = LongIlaFromArray.create(array);
            LongIla targetIla = LongIlaFromArray.create(target);
            LongIla actualIla = LongIlaMutate.create(origIla, index, value);

            LongIlaCheck.check(targetIla, actualIla);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
