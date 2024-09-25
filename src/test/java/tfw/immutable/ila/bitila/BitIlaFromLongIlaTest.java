package tfw.immutable.ila.bitila;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaFromArray;

class BitIlaFromLongIlaTest {
    @Test
    void testInvalidParameters() {
        final LongIla longIla = LongIlaFromArray.create(new long[0]);

        assertThrows(IllegalArgumentException.class, () -> BitIlaFromLongIla.create(null, 0, 0));
        assertThrows(IllegalArgumentException.class, () -> BitIlaFromLongIla.create(longIla, -1, 0));
        assertThrows(IllegalArgumentException.class, () -> BitIlaFromLongIla.create(longIla, 64, 0));
        assertThrows(IllegalArgumentException.class, () -> BitIlaFromLongIla.create(longIla, 0, -1));
    }

    @Test
    void testGetArguments() throws IOException {
        final LongIla longIla = LongIlaFromArray.create(new long[1]);

        BitIlaCheck.checkGetArguments(BitIlaFromLongIla.create(longIla, 0, 64));
    }

    @Test
    void testExhaustive() throws IOException {
        final int numberOfLongs = 3;
        final long[] originalLongs = new long[numberOfLongs];
        final long[] expectedLongs = new long[numberOfLongs];

        for (int length = 0; length < originalLongs.length * Long.SIZE; length++) {
            for (int j = 0; j < expectedLongs.length * Long.SIZE; j++) {
                BitIlaUtil.setBit(expectedLongs, j, j < length ? 1 : 0);
            }

            for (int start = 0; start < Long.SIZE - length; start++) {
                for (int j = 0; j < originalLongs.length * Long.SIZE; j++) {
                    BitIlaUtil.setBit(originalLongs, j, start <= j && j < start + length ? 1 : 0);
                }

                final LongIla longIla = LongIlaFromArray.create(originalLongs);
                final BitIla bitIla = BitIlaFromLongIla.create(longIla, start, length);

                assertEquals(length, bitIla.lengthInBits());

                final long[] actualLongs = new long[numberOfLongs + 1];
                final long[] modExpectedLongs = new long[numberOfLongs + 1];

                for (int getLength = 1; getLength <= length; getLength++) {
                    for (int getOffset = 0; getOffset < Long.SIZE; getOffset++) {
                        for (int getStart = 0; getStart < bitIla.lengthInBits() - getLength; getStart++) {
                            Arrays.fill(modExpectedLongs, 0);
                            Arrays.fill(actualLongs, 0);

                            BitIlaUtil.copy(modExpectedLongs, getOffset, expectedLongs, getStart, getLength);
                            bitIla.get(actualLongs, getOffset, getStart, getLength);

                            assertArrayEquals(modExpectedLongs, actualLongs);
                        }
                    }
                }
            }
        }
    }
}
