package tfw.immutable.ila.bitila;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.longila.LongIlaFromArray;

final class BitIlaAndTest {
    @Test
    void andArgumentsTest() {
        assertThatThrownBy(() -> BitIlaAnd.and(null, 0, new long[1], 0, 0))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> BitIlaAnd.and(new long[1], -1, new long[1], 0, 0))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> BitIlaAnd.and(new long[1], 0, new long[1], 0, 0))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> BitIlaAnd.and(new long[1], 0, new long[1], -1, 0))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> BitIlaAnd.and(new long[1], 0, new long[1], 0, -1))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> BitIlaAnd.and(new long[1], 60, new long[1], 0, 5))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> BitIlaAnd.and(new long[1], 0, new long[1], 60, 5))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void createArgumentsTest() {
        final BitIla bitIla = BitIlaFromLongIla.create(LongIlaFromArray.create(new long[1]), 0, 64);

        assertThatThrownBy(() -> BitIlaAnd.create(null, bitIla)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> BitIlaAnd.create(bitIla, null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void lengthTest() throws IOException {
        final long length = 64;
        final BitIla bitIla1 = BitIlaFromLongIla.create(LongIlaFromArray.create(new long[1]), 0, length);
        final BitIla bitIla2 = BitIlaFromLongIla.create(LongIlaFromArray.create(new long[1]), 0, length);
        final BitIla bitIla = BitIlaAnd.create(bitIla1, bitIla2);

        assertThat(bitIla.lengthInBits()).isEqualTo(length);
    }

    @Test
    void getArgumentsTest() throws IOException {
        final BitIla bitIla1 = BitIlaFromLongIla.create(LongIlaFromArray.create(new long[1]), 0, 64);
        final BitIla bitIla2 = BitIlaFromLongIla.create(LongIlaFromArray.create(new long[1]), 0, 64);

        BitIlaCheck.checkGetArguments(BitIlaAnd.create(bitIla1, bitIla2));
    }

    @Test
    void oneTest() {
        final int numberOfLongs = 3;
        final long[] zeroBitLongs = new long[numberOfLongs];
        final long[] oneBitLongs = new long[numberOfLongs];
        final long[] expectedLongs = new long[numberOfLongs];

        for (int length = 0; length < Long.SIZE; length++) {
            for (int j = 0; j < expectedLongs.length * Long.SIZE; j++) {
                BitIlaUtil.setBit(zeroBitLongs, j, j < length ? 0 : 1);
                BitIlaUtil.setBit(oneBitLongs, j, j < length ? 1 : 0);
            }
        }
    }
}
