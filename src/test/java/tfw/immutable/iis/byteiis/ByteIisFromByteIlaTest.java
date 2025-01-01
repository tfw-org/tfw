package tfw.immutable.iis.byteiis;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaFromArray;

final class ByteIisFromByteIlaTest {
    @Test
    void argumentsTest() {
        assertThatThrownBy(() -> ByteIisFromByteIla.create(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void readTest() throws IOException {
        final byte[] expectedArray = new byte[12];
        final ByteIla ila = ByteIlaFromArray.create(expectedArray);

        try (ByteIis iis = ByteIisFromByteIla.create(ila)) {
            final byte[] actualArray = new byte[expectedArray.length];

            for (int i = 0; i < actualArray.length; i += actualArray.length / 4) {
                assertThat(iis.read(actualArray, i, actualArray.length / 4)).isEqualTo(actualArray.length / 4);
            }

            assertThat(iis.read(new byte[1], 0, 1)).isEqualTo(-1);
            assertThat(actualArray).isEqualTo(expectedArray);
        }
    }

    @Test
    void read2Test() throws IOException {
        final byte[] array = new byte[12];
        final byte[] expectedArray = new byte[array.length];

        Arrays.fill(array, (byte) 1);
        Arrays.fill(expectedArray, 0, expectedArray.length, (byte) 0);
        Arrays.fill(expectedArray, 0, expectedArray.length - 1, (byte) 1);

        final ByteIla ila = ByteIlaFromArray.create(array);

        try (ByteIis iis = ByteIisFromByteIla.create(ila)) {
            final byte[] actualArray = new byte[expectedArray.length];

            Arrays.fill(actualArray, (byte) 0);

            assertThat(iis.skip(1)).isEqualTo(1);
            assertThat(iis.read(actualArray, 0, actualArray.length)).isEqualTo(expectedArray.length - 1);
            assertThat(actualArray).isEqualTo(expectedArray);
        }
    }

    @Test
    void skipTest() throws IOException {
        final byte[] expectedArray = new byte[12];
        final ByteIla ila = ByteIlaFromArray.create(expectedArray);

        try (ByteIis iis = ByteIisFromByteIla.create(ila)) {
            for (int i = 0; i < 4; i++) {
                assertThat(iis.skip(expectedArray.length / 4)).isEqualTo(expectedArray.length / 4);
            }

            assertThat(iis.skip(1)).isEqualTo(-1);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
