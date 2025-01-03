package tfw.immutable.ila.bitila;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.longila.LongIlaFromArray;

final class BitIlaReverseTest {
    @Test
    void reverseArgumentsTest() {
        assertThatThrownBy(() -> BitIlaReverse.reverse(null, 0, 0)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> BitIlaReverse.reverse(new long[1], -1, 0))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> BitIlaReverse.reverse(new long[1], 0, -1))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void createArgumentsTest() {
        assertThatThrownBy(() -> BitIlaReverse.create(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void lengthTest() throws IOException {
        final long length = 64;
        final BitIla bitIla1 = BitIlaFromLongIla.create(LongIlaFromArray.create(new long[1]), 0, length);
        final BitIla bitIla = BitIlaReverse.create(bitIla1);

        assertThat(bitIla.lengthInBits()).isEqualTo(length);
    }

    @Test
    void getArgumentsTest() throws IOException {
        final BitIla bitIla1 = BitIlaFromLongIla.create(LongIlaFromArray.create(new long[1]), 0, 64);

        BitIlaCheck.checkGetArguments(BitIlaReverse.create(bitIla1));
    }
}
