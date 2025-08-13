package tfw.immutable.iis.shortiis;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.shortila.ShortIla;
import tfw.immutable.ila.shortila.ShortIlaFromArray;

final class ShortIisFromShortIlaTest {
    @Test
    void argumentsTest() {
        assertThatThrownBy(() -> ShortIisFromShortIla.create(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void readTest() throws IOException {
        final short[] expectedArray = new short[12];
        final ShortIla ila = ShortIlaFromArray.create(expectedArray);

        try (ShortIis iis = ShortIisFromShortIla.create(ila)) {
            final short[] actualArray = new short[expectedArray.length];

            for (int i = 0; i < actualArray.length; i += actualArray.length / 4) {
                assertThat(iis.read(actualArray, i, actualArray.length / 4)).isEqualTo(actualArray.length / 4);
            }

            assertThat(iis.read(new short[1], 0, 1)).isEqualTo(-1);
            assertThat(actualArray).isEqualTo(expectedArray);
        }
    }

    @Test
    void read2Test() throws IOException {
        final short[] array = new short[12];
        final short[] expectedArray = new short[array.length];

        Arrays.fill(array, (short) 1);
        Arrays.fill(expectedArray, 0, expectedArray.length, (short) 0);
        Arrays.fill(expectedArray, 0, expectedArray.length - 1, (short) 1);

        final ShortIla ila = ShortIlaFromArray.create(array);

        try (ShortIis iis = ShortIisFromShortIla.create(ila)) {
            final short[] actualArray = new short[expectedArray.length];

            Arrays.fill(actualArray, (short) 0);

            assertThat(iis.skip(1)).isEqualTo(1);
            assertThat(iis.read(actualArray, 0, actualArray.length)).isEqualTo(expectedArray.length - 1);
            assertThat(actualArray).isEqualTo(expectedArray);
        }
    }

    @Test
    void skipTest() throws IOException {
        final short[] expectedArray = new short[12];
        final ShortIla ila = ShortIlaFromArray.create(expectedArray);

        try (ShortIis iis = ShortIisFromShortIla.create(ila)) {
            for (int i = 0; i < 4; i++) {
                assertThat(iis.skip(expectedArray.length / 4)).isEqualTo(expectedArray.length / 4);
            }

            assertThat(iis.skip(1)).isEqualTo(-1);
            assertThat(iis.skip(1)).isEqualTo(-1);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
