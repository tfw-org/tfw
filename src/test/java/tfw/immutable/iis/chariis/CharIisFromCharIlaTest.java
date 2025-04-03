package tfw.immutable.iis.chariis;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.charila.CharIla;
import tfw.immutable.ila.charila.CharIlaFromArray;

final class CharIisFromCharIlaTest {
    @Test
    void argumentsTest() {
        assertThatThrownBy(() -> CharIisFromCharIla.create(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void readTest() throws IOException {
        final char[] expectedArray = new char[12];
        final CharIla ila = CharIlaFromArray.create(expectedArray);

        try (CharIis iis = CharIisFromCharIla.create(ila)) {
            final char[] actualArray = new char[expectedArray.length];

            for (int i = 0; i < actualArray.length; i += actualArray.length / 4) {
                assertThat(iis.read(actualArray, i, actualArray.length / 4)).isEqualTo(actualArray.length / 4);
            }

            assertThat(iis.read(new char[1], 0, 1)).isEqualTo(-1);
            assertThat(actualArray).isEqualTo(expectedArray);
        }
    }

    @Test
    void read2Test() throws IOException {
        final char[] array = new char[12];
        final char[] expectedArray = new char[array.length];

        Arrays.fill(array, (char) 1);
        Arrays.fill(expectedArray, 0, expectedArray.length, (char) 0);
        Arrays.fill(expectedArray, 0, expectedArray.length - 1, (char) 1);

        final CharIla ila = CharIlaFromArray.create(array);

        try (CharIis iis = CharIisFromCharIla.create(ila)) {
            final char[] actualArray = new char[expectedArray.length];

            Arrays.fill(actualArray, (char) 0);

            assertThat(iis.skip(1)).isEqualTo(1);
            assertThat(iis.read(actualArray, 0, actualArray.length)).isEqualTo(expectedArray.length - 1);
            assertThat(actualArray).isEqualTo(expectedArray);
        }
    }

    @Test
    void skipTest() throws IOException {
        final char[] expectedArray = new char[12];
        final CharIla ila = CharIlaFromArray.create(expectedArray);

        try (CharIis iis = CharIisFromCharIla.create(ila)) {
            for (int i = 0; i < 4; i++) {
                assertThat(iis.skip(expectedArray.length / 4)).isEqualTo(expectedArray.length / 4);
            }

            assertThat(iis.skip(1)).isEqualTo(-1);
            assertThat(iis.skip(1)).isEqualTo(-1);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
