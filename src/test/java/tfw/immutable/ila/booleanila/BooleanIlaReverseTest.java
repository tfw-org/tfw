package tfw.immutable.ila.booleanila;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class BooleanIlaReverseTest {
    @Test
    void argumentsTest() {
        final BooleanIla ila = BooleanIlaFromArray.create(new boolean[10]);
        final boolean[] buffer = new boolean[10];

        assertThatThrownBy(() -> BooleanIlaReverse.create(null, buffer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ila == null not allowed!");
        assertThatThrownBy(() -> BooleanIlaReverse.create(ila, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("buffer == null not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final boolean[] array = new boolean[length];
        final boolean[] reversed = new boolean[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = random.nextBoolean();
        }
        for (int ii = 0; ii < reversed.length; ++ii) {
            reversed[ii] = array[length - 1 - ii];
        }
        BooleanIla origIla = BooleanIlaFromArray.create(array);
        BooleanIla targetIla = BooleanIlaFromArray.create(reversed);
        BooleanIla actualIla = BooleanIlaReverse.create(origIla, new boolean[1000]);

        BooleanIlaCheck.check(targetIla, actualIla);
    }

    @Test
    void closeTest() throws IOException {
        final TestCloseBooleanIla testIla = new TestCloseBooleanIla();

        try (BooleanIla ila = BooleanIlaReverse.create(testIla, new boolean[100])) {
            assertThat(ila).isNotNull();
        }

        assertThat(testIla.getNumberOfCloses()).isEqualTo(1);
    }
}
// AUTO GENERATED FROM TEMPLATE
