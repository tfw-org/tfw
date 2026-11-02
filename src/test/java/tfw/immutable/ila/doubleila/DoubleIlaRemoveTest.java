package tfw.immutable.ila.doubleila;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class DoubleIlaRemoveTest {
    @Test
    void argumentsTest() throws Exception {
        final DoubleIla ila = DoubleIlaFromArray.create(new double[10]);
        final long ilaLength = ila.length();

        assertThatThrownBy(() -> DoubleIlaRemove.create(null, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ila == null not allowed!");
        assertThatThrownBy(() -> DoubleIlaRemove.create(ila, -1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("index (=-1) < 0 not allowed!");
        assertThatThrownBy(() -> DoubleIlaRemove.create(ila, ilaLength))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("index (=10) >= ila.length() (=10) not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final double[] array = new double[length];
        final double[] target = new double[length - 1];
        for (int index = 0; index < length; ++index) {
            int targetii = 0;
            for (int ii = 0; ii < array.length; ++ii) {
                array[ii] = random.nextDouble();
                if (ii != index) {
                    target[targetii++] = array[ii];
                }
            }
            DoubleIla origIla = DoubleIlaFromArray.create(array);
            DoubleIla targetIla = DoubleIlaFromArray.create(target);
            DoubleIla actualIla = DoubleIlaRemove.create(origIla, index);

            DoubleIlaCheck.check(targetIla, actualIla);
        }
    }

    @Test
    void closeTest() throws IOException {
        final TestCloseDoubleIla testIla = new TestCloseDoubleIla();

        try (DoubleIla ila = DoubleIlaRemove.create(testIla, 0)) {
            assertThat(ila).isNotNull();
        }

        assertThat(testIla.getNumberOfCloses()).isEqualTo(1);
    }
}
// AUTO GENERATED FROM TEMPLATE
