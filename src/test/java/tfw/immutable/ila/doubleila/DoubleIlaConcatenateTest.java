package tfw.immutable.ila.doubleila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class DoubleIlaConcatenateTest {
    @Test
    void argumentsTest() {
        final DoubleIla ila = DoubleIlaFromArray.create(new double[10]);

        assertThatThrownBy(() -> DoubleIlaConcatenate.create(ila, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("rightIla == null not allowed!");
        assertThatThrownBy(() -> DoubleIlaConcatenate.create(null, ila))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("leftIla == null not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int leftLength = IlaTestDimensions.defaultIlaLength();
        final int rightLength = 1 + random.nextInt(leftLength);
        final double[] leftArray = new double[leftLength];
        final double[] rightArray = new double[rightLength];
        final double[] array = new double[leftLength + rightLength];
        for (int ii = 0; ii < leftArray.length; ++ii) {
            array[ii] = leftArray[ii] = random.nextDouble();
        }
        for (int ii = 0; ii < rightArray.length; ++ii) {
            array[ii + leftLength] = rightArray[ii] = random.nextDouble();
        }
        DoubleIla leftIla = DoubleIlaFromArray.create(leftArray);
        DoubleIla rightIla = DoubleIlaFromArray.create(rightArray);
        DoubleIla targetIla = DoubleIlaFromArray.create(array);
        DoubleIla actualIla = DoubleIlaConcatenate.create(leftIla, rightIla);

        DoubleIlaCheck.check(targetIla, actualIla);
    }
}
// AUTO GENERATED FROM TEMPLATE
