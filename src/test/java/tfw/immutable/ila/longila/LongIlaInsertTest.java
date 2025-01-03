package tfw.immutable.ila.longila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class LongIlaInsertTest {
    @Test
    void argumentsTest() throws Exception {
        final Random random = new Random(0);
        final LongIla ila = LongIlaFromArray.create(new long[10]);
        final long ilaLength = ila.length();
        final long value = random.nextLong();

        assertThatThrownBy(() -> LongIlaInsert.create(null, 0, value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ila == null not allowed!");
        assertThatThrownBy(() -> LongIlaInsert.create(ila, -1, value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("index (=-1) < 0 not allowed!");
        assertThatThrownBy(() -> LongIlaInsert.create(ila, ilaLength + 1, value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("index (=11) > ila.length() (=10) not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final long[] array = new long[length];
        final long[] target = new long[length + 1];
        for (int index = 0; index < length; ++index) {
            final long value = random.nextLong();
            int skipit = 0;
            for (int ii = 0; ii < array.length; ++ii) {
                if (index == ii) {
                    skipit = 1;
                    target[ii] = value;
                }
                target[ii + skipit] = array[ii] = random.nextLong();
            }
            LongIla origIla = LongIlaFromArray.create(array);
            LongIla targetIla = LongIlaFromArray.create(target);
            LongIla actualIla = LongIlaInsert.create(origIla, index, value);

            LongIlaCheck.check(targetIla, actualIla);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
