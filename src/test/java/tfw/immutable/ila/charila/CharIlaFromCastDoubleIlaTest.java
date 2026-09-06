package tfw.immutable.ila.charila;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.doubleila.DoubleIlaFromArray;
import tfw.immutable.ila.doubleila.TestCloseDoubleIla;

final class CharIlaFromCastDoubleIlaTest {
    @Test
    void argumentsTest() {
        final DoubleIla ila = DoubleIlaFromArray.create(new double[10]);

        assertThatThrownBy(() -> CharIlaFromCastDoubleIla.create(null, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("doubleIla == null not allowed!");
        assertThatThrownBy(() -> CharIlaFromCastDoubleIla.create(ila, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("bufferSize (=0) < 1 not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final double[] array = new double[length];
        final char[] target = new char[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = random.nextDouble();
            target[ii] = (char) array[ii];
        }
        DoubleIla ila = DoubleIlaFromArray.create(array);
        CharIla targetIla = CharIlaFromArray.create(target);
        CharIla actualIla = CharIlaFromCastDoubleIla.create(ila, 100);

        CharIlaCheck.check(targetIla, actualIla);
    }

    @Test
    void closeTest() throws IOException {
        final TestCloseDoubleIla testIla = new TestCloseDoubleIla();

        try (CharIla ila = CharIlaFromCastDoubleIla.create(testIla, 100)) {
            assertThat(ila).isNotNull();
        }

        assertThat(testIla.getNumberOfCloses()).isEqualTo(1);
    }
}
// AUTO GENERATED FROM TEMPLATE
