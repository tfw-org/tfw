package tfw.immutable.iis.bitiis;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.bitila.BitIla;
import tfw.immutable.ila.bitila.BitIlaFromLongIla;
import tfw.immutable.ila.bitila.BitIlaUtil;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaFromArray;

final class BitIisFromBitIlaTest {
    @Test
    void argumentsTest() {
        assertThatThrownBy(() -> BitIisFromBitIla.create(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void readTest() throws IOException {
        final int numberOfBits = 12;
        final long[] expectedArray = new long[1];
        final LongIla longIla = LongIlaFromArray.create(expectedArray);
        final BitIla ila = BitIlaFromLongIla.create(longIla, 0, numberOfBits);

        try (BitIis iis = BitIisFromBitIla.create(ila)) {
            final long[] actualArray = new long[expectedArray.length];

            for (int i = 0; i < numberOfBits; i += numberOfBits / 4) {
                assertThat(iis.read(actualArray, i, numberOfBits / 4)).isEqualTo(numberOfBits / 4);
            }

            assertThat(iis.read(new long[1], 0, 1)).isEqualTo(-1);
            assertThat(actualArray).isEqualTo(expectedArray);
        }
    }

    @Test
    void read2Test() throws IOException {
        final int numberOfBits = 12;
        final long[] array = new long[1];
        final long[] expectedArray = new long[array.length];

        for (int i = 0; i < numberOfBits; i++) {
            BitIlaUtil.setBit(array, i, 1);
        }
        for (int i = 0; i < numberOfBits - 1; i++) {
            BitIlaUtil.setBit(expectedArray, i, 1);
        }
        BitIlaUtil.setBit(expectedArray, numberOfBits - 1, 0);

        final LongIla longIla = LongIlaFromArray.create(array);
        final BitIla bitIla = BitIlaFromLongIla.create(longIla, 0, numberOfBits);

        try (BitIis iis = BitIisFromBitIla.create(bitIla)) {
            final long[] actualArray = new long[expectedArray.length];

            for (int i = 0; i < numberOfBits; i++) {
                BitIlaUtil.setBit(actualArray, i, 0);
            }

            assertThat(iis.skip(1)).isEqualTo(1);
            assertThat(iis.read(actualArray, 0, numberOfBits)).isEqualTo(numberOfBits - 1);
            assertThat(actualArray).isEqualTo(expectedArray);
        }
    }

    @Test
    void skipTest() throws IOException {
        final int numberOfBits = 12;
        final long[] expectedArray = new long[1];
        final LongIla longIla = LongIlaFromArray.create(expectedArray);
        final BitIla ila = BitIlaFromLongIla.create(longIla, 0, numberOfBits);

        try (BitIis iis = BitIisFromBitIla.create(ila)) {
            for (int i = 0; i < 4; i++) {
                assertThat(iis.skip(numberOfBits / 4)).isEqualTo(numberOfBits / 4);
            }

            assertThat(iis.skip(1)).isEqualTo(-1);
        }
    }
}
