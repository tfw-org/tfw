package tfw.immutable.ila.byteila;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class ByteIlaDecimateTest {
    @Test
    void argumentsTest() {
        final ByteIla ila = ByteIlaFromArray.create(new byte[10]);
        final byte[] buffer = new byte[10];

        assertThatThrownBy(() -> ByteIlaDecimate.create(null, 2, buffer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ila == null not allowed!");
        assertThatThrownBy(() -> ByteIlaDecimate.create(ila, 2, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("buffer == null not allowed!");
        assertThatThrownBy(() -> ByteIlaDecimate.create(ila, 1, buffer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("factor (=1) < 2 not allowed!");
        assertThatThrownBy(() -> ByteIlaDecimate.create(ila, 2, new byte[0]))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("buffer.length (=0) < 1 not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final byte[] array = new byte[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = (byte) random.nextInt();
        }
        ByteIla ila = ByteIlaFromArray.create(array);
        for (int factor = 2; factor <= length; ++factor) {
            final int targetLength = (length + factor - 1) / factor;
            final byte[] target = new byte[targetLength];
            for (int ii = 0; ii < target.length; ++ii) {
                target[ii] = array[ii * factor];
            }
            ByteIla targetIla = ByteIlaFromArray.create(target);
            ByteIla actualIla = ByteIlaDecimate.create(ila, factor, new byte[100]);

            ByteIlaCheck.check(targetIla, actualIla);
        }
    }

    @Test
    void closeTest() throws IOException {
        final TestCloseByteIla testIla = new TestCloseByteIla();

        try (ByteIla ila = ByteIlaDecimate.create(testIla, 2, new byte[1])) {
            assertThat(ila).isNotNull();
        }

        assertThat(testIla.getNumberOfCloses()).isEqualTo(1);
    }

    @Test
    void largeFactorTest() throws Exception {
        final Random random = new Random(0);
        final byte[] array = new byte[2];
        array[0] = (byte) random.nextInt();
        array[1] = (byte) random.nextInt();

        final ByteIla ila = ByteIlaFromArray.create(array);

        try {
            final ByteIla actualIla = ByteIlaDecimate.create(ila, Long.MAX_VALUE, new byte[1]);

            try {
                assertThat(actualIla.length()).isEqualTo(1);

                final byte[] destination = new byte[1];
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
