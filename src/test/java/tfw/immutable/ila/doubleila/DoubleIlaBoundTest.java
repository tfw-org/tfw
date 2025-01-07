package tfw.immutable.ila.doubleila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class DoubleIlaBoundTest {
    @Test
    void argumentsTest() {
        final double low = 5;
        final double high = 10;
        final DoubleIla ila = DoubleIlaFromArray.create(new double[10]);

        assertThatThrownBy(() -> DoubleIlaBound.create(null, low, high))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ila == null not allowed!");
        assertThatThrownBy(() -> DoubleIlaBound.create(ila, high, low))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("minimum (=" + high + ") > maximum (=" + low + ") not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final double[] array = new double[length];
        final double[] target = new double[length];
        double minimum = random.nextDouble();
        double maximum = random.nextDouble();
        if (minimum > maximum) {
            double tmp = minimum;
            minimum = maximum;
            maximum = tmp;
        }
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = random.nextDouble();
            target[ii] = array[ii];
            if (target[ii] < minimum) {
                target[ii] = minimum;
            } else if (target[ii] > maximum) {
                target[ii] = maximum;
            }
        }
        DoubleIla ila = DoubleIlaFromArray.create(array);
        DoubleIla targetIla = DoubleIlaFromArray.create(target);
        DoubleIla actualIla = DoubleIlaBound.create(ila, minimum, maximum);

        DoubleIlaCheck.check(targetIla, actualIla);
    }
}
// AUTO GENERATED FROM TEMPLATE
