package tfw.immutable.ila.doubleila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class DoubleIlaDivideTest {
    @Test
    void argumentsTest() {
        final DoubleIla ila1 = DoubleIlaFromArray.create(new double[10]);
        final DoubleIla ila2 = DoubleIlaFromArray.create(new double[20]);

        assertThatThrownBy(() -> DoubleIlaDivide.create(null, ila2, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("leftIla == null not allowed!");
        assertThatThrownBy(() -> DoubleIlaDivide.create(ila1, null, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("rightIla == null not allowed!");
        assertThatThrownBy(() -> DoubleIlaDivide.create(ila1, ila2, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("leftIla.length() (=10) != rightIla.length() (=20) not allowed!");
        assertThatThrownBy(() -> DoubleIlaDivide.create(ila1, ila1, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("bufferSize (=0) < 1 not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final double[] leftArray = new double[length];
        final double[] rightArray = new double[length];
        final double[] array = new double[length];
        for (int ii = 0; ii < leftArray.length; ++ii) {
            leftArray[ii] = random.nextDouble();
            rightArray[ii] = random.nextDouble();
            array[ii] = leftArray[ii] / rightArray[ii];
        }
        DoubleIla leftIla = DoubleIlaFromArray.create(leftArray);
        DoubleIla rightIla = DoubleIlaFromArray.create(rightArray);
        DoubleIla targetIla = DoubleIlaFromArray.create(array);
        DoubleIla actualIla = DoubleIlaDivide.create(leftIla, rightIla, 100);

        DoubleIlaCheck.check(targetIla, actualIla);
    }
}
// AUTO GENERATED FROM TEMPLATE
