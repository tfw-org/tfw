package tfw.immutable.ila.charila;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class CharIlaDecimateTest {
    @Test
    void argumentsTest() {
        final CharIla ila = CharIlaFromArray.create(new char[10]);
        final char[] buffer = new char[10];

        assertThatThrownBy(() -> CharIlaDecimate.create(null, 2, buffer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ila == null not allowed!");
        assertThatThrownBy(() -> CharIlaDecimate.create(ila, 2, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("buffer == null not allowed!");
        assertThatThrownBy(() -> CharIlaDecimate.create(ila, 1, buffer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("factor (=1) < 2 not allowed!");
        assertThatThrownBy(() -> CharIlaDecimate.create(ila, 2, new char[0]))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("buffer.length (=0) < 1 not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final char[] array = new char[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = (char) random.nextInt();
        }
        CharIla ila = CharIlaFromArray.create(array);
        for (int factor = 2; factor <= length; ++factor) {
            final int targetLength = (length + factor - 1) / factor;
            final char[] target = new char[targetLength];
            for (int ii = 0; ii < target.length; ++ii) {
                target[ii] = array[ii * factor];
            }
            CharIla targetIla = CharIlaFromArray.create(target);
            CharIla actualIla = CharIlaDecimate.create(ila, factor, new char[100]);

            CharIlaCheck.check(targetIla, actualIla);
        }
    }

    @Test
    void closeTest() throws IOException {
        final TestCloseCharIla testIla = new TestCloseCharIla();

        try (CharIla ila = CharIlaDecimate.create(testIla, 2, new char[1])) {
            assertThat(ila).isNotNull();
        }

        assertThat(testIla.getNumberOfCloses()).isEqualTo(1);
    }

    @Test
    void largeFactorTest() throws Exception {
        final Random random = new Random(0);
        final char[] array = new char[2];
        array[0] = (char) random.nextInt();
        array[1] = (char) random.nextInt();

        final CharIla ila = CharIlaFromArray.create(array);

        try {
            final CharIla actualIla = CharIlaDecimate.create(ila, Long.MAX_VALUE, new char[1]);

            try {
                assertThat(actualIla.length()).isEqualTo(1);

                final char[] destination = new char[1];
                actualIla.get(destination, 0, 0, 1);

                assertThat(destination[0]).isEqualTo(array[0]);
            } finally {
                actualIla.close();
            }
        } finally {
            ila.close();
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
