package tfw.immutable.ila.doubleila;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class DoubleIlaScalarMultiplyTest {
    @Test
    void argumentsTest() {
        final Random random = new Random(0);
        final double value = random.nextDouble();

        assertThatThrownBy(() -> DoubleIlaScalarMultiply.create(null, value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ila == null not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final double scalar = random.nextDouble();
        final double[] array = new double[length];
        final double[] target = new double[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = random.nextDouble();
            target[ii] = array[ii] * scalar;
        }
        DoubleIla ila = DoubleIlaFromArray.create(array);
        DoubleIla targetIla = DoubleIlaFromArray.create(target);
        DoubleIla actualIla = DoubleIlaScalarMultiply.create(ila, scalar);

        DoubleIlaCheck.check(targetIla, actualIla);
    }

    @Test
    void closeTest() throws IOException {
        final Random random = new Random(0);
        final TestCloseDoubleIla testIla = new TestCloseDoubleIla();

        try (DoubleIla ila = DoubleIlaScalarMultiply.create(testIla, random.nextDouble())) {
            assertThat(ila).isNotNull();
        }

        assertThat(testIla.getNumberOfCloses()).isEqualTo(1);
    }
}
// AUTO GENERATED FROM TEMPLATE
