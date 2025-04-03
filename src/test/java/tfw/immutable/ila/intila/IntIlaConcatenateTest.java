package tfw.immutable.ila.intila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class IntIlaConcatenateTest {
    @Test
    void argumentsTest() {
        final IntIla ila = IntIlaFromArray.create(new int[10]);

        assertThatThrownBy(() -> IntIlaConcatenate.create(ila, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("rightIla == null not allowed!");
        assertThatThrownBy(() -> IntIlaConcatenate.create(null, ila))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("leftIla == null not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int leftLength = IlaTestDimensions.defaultIlaLength();
        final int rightLength = 1 + random.nextInt(leftLength);
        final int[] leftArray = new int[leftLength];
        final int[] rightArray = new int[rightLength];
        final int[] array = new int[leftLength + rightLength];
        for (int ii = 0; ii < leftArray.length; ++ii) {
            array[ii] = leftArray[ii] = random.nextInt();
        }
        for (int ii = 0; ii < rightArray.length; ++ii) {
            array[ii + leftLength] = rightArray[ii] = random.nextInt();
        }
        IntIla leftIla = IntIlaFromArray.create(leftArray);
        IntIla rightIla = IntIlaFromArray.create(rightArray);
        IntIla targetIla = IntIlaFromArray.create(array);
        IntIla actualIla = IntIlaConcatenate.create(leftIla, rightIla);

        IntIlaCheck.check(targetIla, actualIla);
    }
}
// AUTO GENERATED FROM TEMPLATE
