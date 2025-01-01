package tfw.immutable.iis.longiis;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaFromArray;

final class LongIisFromLongIlaTest {
    @Test
    void argumentsTest() {
        assertThatThrownBy(() -> LongIisFromLongIla.create(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void readTest() throws IOException {
        final long[] expectedArray = new long[12];
        final LongIla ila = LongIlaFromArray.create(expectedArray);

        try (LongIis iis = LongIisFromLongIla.create(ila)) {
            final long[] actualArray = new long[expectedArray.length];

            for (int i = 0; i < actualArray.length; i += actualArray.length / 4) {
                assertThat(iis.read(actualArray, i, actualArray.length / 4)).isEqualTo(actualArray.length / 4);
            }

            assertThat(iis.read(new long[1], 0, 1)).isEqualTo(-1);
            assertThat(actualArray).isEqualTo(expectedArray);
        }
    }

    @Test
    void read2Test() throws IOException {
        final long[] array = new long[12];
        final long[] expectedArray = new long[array.length];

        Arrays.fill(array, 1L);
        Arrays.fill(expectedArray, 0, expectedArray.length, 0L);
        Arrays.fill(expectedArray, 0, expectedArray.length - 1, 1L);

        final LongIla ila = LongIlaFromArray.create(array);

        try (LongIis iis = LongIisFromLongIla.create(ila)) {
            final long[] actualArray = new long[expectedArray.length];

            Arrays.fill(actualArray, 0L);

            assertThat(iis.skip(1)).isEqualTo(1);
            assertThat(iis.read(actualArray, 0, actualArray.length)).isEqualTo(expectedArray.length - 1);
            assertThat(actualArray).isEqualTo(expectedArray);
        }
    }

    @Test
    void skipTest() throws IOException {
        final long[] expectedArray = new long[12];
        final LongIla ila = LongIlaFromArray.create(expectedArray);

        try (LongIis iis = LongIisFromLongIla.create(ila)) {
            for (int i = 0; i < 4; i++) {
                assertThat(iis.skip(expectedArray.length / 4)).isEqualTo(expectedArray.length / 4);
            }

            assertThat(iis.skip(1)).isEqualTo(-1);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
