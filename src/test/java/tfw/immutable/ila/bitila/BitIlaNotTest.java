package tfw.immutable.ila.bitila;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.longila.LongIlaFromArray;

final class BitIlaNotTest {
    @Test
    void notArgumentsTest() {
        assertThatThrownBy(() -> BitIlaNot.not(null, 0, 0)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> BitIlaNot.not(new long[1], -1, 0)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> BitIlaNot.not(new long[1], 0, -1)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void createArgumentsTest() {
        assertThatThrownBy(() -> BitIlaNot.create(null));
    }

    @Test
    void lengthTest() throws IOException {
        final long length = 64;
        final BitIla bitIla = BitIlaFromLongIla.create(LongIlaFromArray.create(new long[1]), 0, length);
        final BitIla bitIlaNot = BitIlaNot.create(bitIla);

        assertThat(bitIlaNot.lengthInBits()).isEqualTo(length);
    }

    @Test
    void getArgumentsTest() throws IOException {
        final BitIla bitIla1 = BitIlaFromLongIla.create(LongIlaFromArray.create(new long[1]), 0, 64);

        BitIlaCheck.checkGetArguments(BitIlaNot.create(bitIla1));
    }
}
