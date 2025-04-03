package tfw.immutable.ila.intila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class IntIlaMutateTest {
    @Test
    void argumentsTest() throws Exception {
        final Random random = new Random(0);
        final IntIla ila = IntIlaFromArray.create(new int[10]);
        final long ilaLength = ila.length();
        final int value = random.nextInt();

        assertThatThrownBy(() -> IntIlaMutate.create(null, 0, value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ila == null not allowed!");
        assertThatThrownBy(() -> IntIlaMutate.create(ila, -1, value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("index (=-1) < 0 not allowed!");
        assertThatThrownBy(() -> IntIlaMutate.create(ila, ilaLength, value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("index (=10) >= ila.length() (=10) not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final int[] array = new int[length];
        final int[] target = new int[length];
        for (int index = 0; index < length; ++index) {
            for (int ii = 0; ii < array.length; ++ii) {
                array[ii] = target[ii] = random.nextInt();
            }
            final int value = random.nextInt();
            target[index] = value;
            IntIla origIla = IntIlaFromArray.create(array);
            IntIla targetIla = IntIlaFromArray.create(target);
            IntIla actualIla = IntIlaMutate.create(origIla, index, value);

            IntIlaCheck.check(targetIla, actualIla);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
