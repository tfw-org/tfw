package tfw.immutable.ila.longila;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class LongIlaScalarMultiplyTest {
    @Test
    void argumentsTest() {
        final Random random = new Random(0);
        final long value = random.nextLong();

        assertThatThrownBy(() -> LongIlaScalarMultiply.create(null, value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ila == null not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final long scalar = random.nextLong();
        final long[] array = new long[length];
        final long[] target = new long[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = random.nextLong();
            target[ii] = array[ii] * scalar;
        }
        LongIla ila = LongIlaFromArray.create(array);
        LongIla targetIla = LongIlaFromArray.create(target);
        LongIla actualIla = LongIlaScalarMultiply.create(ila, scalar);

        LongIlaCheck.check(targetIla, actualIla);
    }

    @Test
    void closeTest() throws IOException {
        final Random random = new Random(0);
        final TestCloseLongIla testIla = new TestCloseLongIla();

        try (LongIla ila = LongIlaScalarMultiply.create(testIla, random.nextLong())) {
            assertThat(ila).isNotNull();
        }

        assertThat(testIla.getNumberOfCloses()).isEqualTo(1);
    }
}
// AUTO GENERATED FROM TEMPLATE
