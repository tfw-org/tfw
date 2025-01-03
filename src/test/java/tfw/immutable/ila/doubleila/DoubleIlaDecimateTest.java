package tfw.immutable.ila.doubleila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class DoubleIlaDecimateTest {
    @Test
    void argumentsTest() {
        final DoubleIla ila = DoubleIlaFromArray.create(new double[10]);
        final double[] buffer = new double[10];

        assertThatThrownBy(() -> DoubleIlaDecimate.create(null, 2, buffer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ila == null not allowed!");
        assertThatThrownBy(() -> DoubleIlaDecimate.create(ila, 2, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("buffer == null not allowed!");
        assertThatThrownBy(() -> DoubleIlaDecimate.create(ila, 1, buffer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("factor (=1) < 2 not allowed!");
        assertThatThrownBy(() -> DoubleIlaDecimate.create(ila, 2, new double[0]))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("buffer.length (=0) < 1 not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final double[] array = new double[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = random.nextDouble();
        }
        DoubleIla ila = DoubleIlaFromArray.create(array);
        for (int factor = 2; factor <= length; ++factor) {
            final int targetLength = (length + factor - 1) / factor;
            final double[] target = new double[targetLength];
            for (int ii = 0; ii < target.length; ++ii) {
                target[ii] = array[ii * factor];
            }
            DoubleIla targetIla = DoubleIlaFromArray.create(target);
            DoubleIla actualIla = DoubleIlaDecimate.create(ila, factor, new double[100]);

            DoubleIlaCheck.check(targetIla, actualIla);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
