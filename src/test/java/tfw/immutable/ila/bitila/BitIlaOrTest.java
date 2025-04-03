package tfw.immutable.ila.bitila;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.longila.LongIlaFromArray;

final class BitIlaOrTest {
    @Test
    void orArgumentsTest() {
        assertThatThrownBy(() -> BitIlaOr.or(null, 0, new long[1], 0, 0)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> BitIlaOr.or(new long[1], -1, new long[1], 0, 0))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> BitIlaOr.or(new long[1], 0, new long[1], 0, 0))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> BitIlaOr.or(new long[1], 0, new long[1], -1, 0))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> BitIlaOr.or(new long[1], 0, new long[1], 0, -1))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> BitIlaOr.or(new long[1], 60, new long[1], 0, 5))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> BitIlaOr.or(new long[1], 0, new long[1], 60, 5))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void createArgumentsTest() {
        final BitIla bitIla = BitIlaFromLongIla.create(LongIlaFromArray.create(new long[1]), 0, 64);

        assertThatThrownBy(() -> BitIlaOr.create(null, bitIla)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> BitIlaOr.create(bitIla, null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void lengthTest() throws IOException {
        final long length = 64;
        final BitIla bitIla1 = BitIlaFromLongIla.create(LongIlaFromArray.create(new long[1]), 0, length);
        final BitIla bitIla2 = BitIlaFromLongIla.create(LongIlaFromArray.create(new long[1]), 0, length);
        final BitIla bitIla = BitIlaOr.create(bitIla1, bitIla2);

        assertThat(bitIla.lengthInBits()).isEqualTo(length);
    }

    @Test
    void getArgumentsTest() throws IOException {
        final BitIla bitIla1 = BitIlaFromLongIla.create(LongIlaFromArray.create(new long[1]), 0, 64);
        final BitIla bitIla2 = BitIlaFromLongIla.create(LongIlaFromArray.create(new long[1]), 0, 64);

        BitIlaCheck.checkGetArguments(BitIlaOr.create(bitIla1, bitIla2));
    }
}
