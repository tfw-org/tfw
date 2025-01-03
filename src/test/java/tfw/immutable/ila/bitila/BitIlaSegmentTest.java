package tfw.immutable.ila.bitila;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.longila.LongIlaFromArray;

final class BitIlaSegmentTest {
    @Test
    void createArgumentsTest() {
        final BitIla bitIla = BitIlaFromLongIla.create(LongIlaFromArray.create(new long[1]), 0, 64);

        assertThatThrownBy(() -> BitIlaSegment.create(null, 0, 0)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> BitIlaSegment.create(bitIla, -1, 0)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> BitIlaSegment.create(bitIla, 0, -1)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void lengthTest() throws IOException {
        final long length = 128;
        final BitIla bitIla1 = BitIlaFromLongIla.create(LongIlaFromArray.create(new long[2]), 0, length);
        final BitIla bitIla = BitIlaSegment.create(bitIla1, 32, 64);

        assertThat(bitIla.lengthInBits()).isEqualTo(length / 2);
    }

    @Test
    void getArgumentsTest() throws IOException {
        final BitIla bitIla1 = BitIlaFromLongIla.create(LongIlaFromArray.create(new long[2]), 0, 128);

        BitIlaCheck.checkGetArguments(BitIlaSegment.create(bitIla1, 32, 64));
    }
}
