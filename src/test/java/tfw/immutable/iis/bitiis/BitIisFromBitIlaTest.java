package tfw.immutable.iis.bitiis;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.bitila.BitIla;
import tfw.immutable.ila.bitila.BitIlaFromLongIla;
import tfw.immutable.ila.bitila.BitIlaUtil;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaFromArray;

class BitIisFromBitIlaTest {
    @Test
    void testArguments() {
        assertThrows(IllegalArgumentException.class, () -> BitIisFromBitIla.create(null));
    }

    @Test
    void testRead() throws IOException {
        final int numberOfBits = 12;
        final long[] expectedArray = new long[1];
        final LongIla longIla = LongIlaFromArray.create(expectedArray);
        final BitIla ila = BitIlaFromLongIla.create(longIla, 0, numberOfBits);

        try (BitIis iis = BitIisFromBitIla.create(ila)) {
            final long[] actualArray = new long[expectedArray.length];

            for (int i = 0; i < numberOfBits; i += numberOfBits / 4) {
                assertEquals(numberOfBits / 4, iis.read(actualArray, i, numberOfBits / 4));
            }

            assertEquals(-1, iis.read(new long[1], 0, 1));
            assertArrayEquals(expectedArray, actualArray);
        }
    }

    @Test
    void testRead2() throws IOException {
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

            assertEquals(1, iis.skip(1));
            assertEquals(numberOfBits - 1, iis.read(actualArray, 0, numberOfBits));
            assertArrayEquals(expectedArray, actualArray);
        }
    }

    @Test
    void testSkip() throws IOException {
        final int numberOfBits = 12;
        final long[] expectedArray = new long[1];
        final LongIla longIla = LongIlaFromArray.create(expectedArray);
        final BitIla ila = BitIlaFromLongIla.create(longIla, 0, numberOfBits);

        try (BitIis iis = BitIisFromBitIla.create(ila)) {
            for (int i = 0; i < 4; i++) {
                assertEquals(numberOfBits / 4, iis.skip(numberOfBits / 4));
            }

            assertEquals(-1, iis.skip(1));
        }
    }
}
