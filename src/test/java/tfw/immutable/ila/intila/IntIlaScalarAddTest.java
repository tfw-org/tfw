package tfw.immutable.ila.intila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class IntIlaScalarAddTest {
    @Test
    void argumentsTest() {
        final Random random = new Random(0);
        final int value = random.nextInt();

        assertThatThrownBy(() -> IntIlaScalarAdd.create(null, value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ila == null not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final int scalar = random.nextInt();
        final int[] array = new int[length];
        final int[] target = new int[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = random.nextInt();
            target[ii] = array[ii] + scalar;
        }
        IntIla ila = IntIlaFromArray.create(array);
        IntIla targetIla = IntIlaFromArray.create(target);
        IntIla actualIla = IntIlaScalarAdd.create(ila, scalar);

        IntIlaCheck.check(targetIla, actualIla);
    }
}
// AUTO GENERATED FROM TEMPLATE
