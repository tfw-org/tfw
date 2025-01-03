package tfw.immutable.ila.bitila;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import org.junit.jupiter.api.Test;

final class BitIlaFillTest {
    @Test
    void fillArgumentsTest() {
        assertThatThrownBy(() -> BitIlaFill.fill(null, 0, 0, true)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> BitIlaFill.fill(new long[1], -1, 0, true))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> BitIlaFill.fill(new long[1], 0, -1, true))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void createArgumentsTest() {
        assertThatThrownBy(() -> BitIlaFill.create(true, -1)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void lengthTest() throws IOException {
        final long length = 64;
        final BitIla bitIla = BitIlaFill.create(true, length);

        assertThat(bitIla.lengthInBits()).isEqualTo(length);
    }

    @Test
    void getArgumentsTest() throws IOException {
        BitIlaCheck.checkGetArguments(BitIlaFill.create(true, 64));
    }
}
