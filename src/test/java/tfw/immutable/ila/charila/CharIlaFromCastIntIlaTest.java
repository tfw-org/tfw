package tfw.immutable.ila.charila;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.intila.IntIla;
import tfw.immutable.ila.intila.IntIlaFromArray;
import tfw.immutable.ila.intila.TestCloseIntIla;

final class CharIlaFromCastIntIlaTest {
    @Test
    void argumentsTest() {
        final IntIla ila = IntIlaFromArray.create(new int[10]);

        assertThatThrownBy(() -> CharIlaFromCastIntIla.create(null, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("intIla == null not allowed!");
        assertThatThrownBy(() -> CharIlaFromCastIntIla.create(ila, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("bufferSize (=0) < 1 not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final int[] array = new int[length];
        final char[] target = new char[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = random.nextInt();
            target[ii] = (char) array[ii];
        }
        IntIla ila = IntIlaFromArray.create(array);
        CharIla targetIla = CharIlaFromArray.create(target);
        CharIla actualIla = CharIlaFromCastIntIla.create(ila, 100);

        CharIlaCheck.check(targetIla, actualIla);
    }

    @Test
    void closeTest() throws IOException {
        final TestCloseIntIla testIla = new TestCloseIntIla();

        try (CharIla ila = CharIlaFromCastIntIla.create(testIla, 100)) {
            assertThat(ila).isNotNull();
        }

        assertThat(testIla.getNumberOfCloses()).isEqualTo(1);
    }
}
// AUTO GENERATED FROM TEMPLATE
