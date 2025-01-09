package tfw.immutable.ila.intila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class IntIlaMultiplyTest {
    @Test
    void argumentsTest() {
        final IntIla ila1 = IntIlaFromArray.create(new int[10]);
        final IntIla ila2 = IntIlaFromArray.create(new int[20]);

        assertThatThrownBy(() -> IntIlaMultiply.create(null, ila1, 10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("leftIla == null not allowed!");
        assertThatThrownBy(() -> IntIlaMultiply.create(ila1, null, 10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("rightIla == null not allowed!");
        assertThatThrownBy(() -> IntIlaMultiply.create(ila1, ila1, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("bufferSize (=0) < 1 not allowed!");
        assertThatThrownBy(() -> IntIlaMultiply.create(ila1, ila2, 10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("leftIla.length() (=10) != rightIla.length() (=20) not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final int[] leftArray = new int[length];
        final int[] rightArray = new int[length];
        final int[] array = new int[length];
        for (int ii = 0; ii < leftArray.length; ++ii) {
            leftArray[ii] = random.nextInt();
            rightArray[ii] = random.nextInt();
            array[ii] = leftArray[ii] * rightArray[ii];
        }
        IntIla leftIla = IntIlaFromArray.create(leftArray);
        IntIla rightIla = IntIlaFromArray.create(rightArray);
        IntIla targetIla = IntIlaFromArray.create(array);
        IntIla actualIla = IntIlaMultiply.create(leftIla, rightIla, 100);

        IntIlaCheck.check(targetIla, actualIla);
    }
}
// AUTO GENERATED FROM TEMPLATE
