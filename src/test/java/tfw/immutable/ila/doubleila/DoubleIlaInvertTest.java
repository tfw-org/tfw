package tfw.immutable.ila.doubleila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class DoubleIlaInvertTest {
    @Test
    void argumentsTest() throws Exception {
        assertThatThrownBy(() -> DoubleIlaInvert.create(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ila == null not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final double[] array = new double[length];
        final double[] target = new double[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = random.nextDouble();
            target[ii] = (double) 1 / array[ii];
        }
        DoubleIla ila = DoubleIlaFromArray.create(array);
        DoubleIla targetIla = DoubleIlaFromArray.create(target);
        DoubleIla actualIla = DoubleIlaInvert.create(ila);

        DoubleIlaCheck.check(targetIla, actualIla);
    }
}
// AUTO GENERATED FROM TEMPLATE
