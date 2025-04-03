package tfw.immutable.ila.intila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class IntIlaInsertTest {
    @Test
    void argumentsTest() throws Exception {
        final Random random = new Random(0);
        final IntIla ila = IntIlaFromArray.create(new int[10]);
        final long ilaLength = ila.length();
        final int value = random.nextInt();

        assertThatThrownBy(() -> IntIlaInsert.create(null, 0, value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ila == null not allowed!");
        assertThatThrownBy(() -> IntIlaInsert.create(ila, -1, value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("index (=-1) < 0 not allowed!");
        assertThatThrownBy(() -> IntIlaInsert.create(ila, ilaLength + 1, value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("index (=11) > ila.length() (=10) not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final int[] array = new int[length];
        final int[] target = new int[length + 1];
        for (int index = 0; index < length; ++index) {
            final int value = random.nextInt();
            int skipit = 0;
            for (int ii = 0; ii < array.length; ++ii) {
                if (index == ii) {
                    skipit = 1;
                    target[ii] = value;
                }
                target[ii + skipit] = array[ii] = random.nextInt();
            }
            IntIla origIla = IntIlaFromArray.create(array);
            IntIla targetIla = IntIlaFromArray.create(target);
            IntIla actualIla = IntIlaInsert.create(origIla, index, value);

            IntIlaCheck.check(targetIla, actualIla);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
