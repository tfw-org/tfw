package tfw.immutable.ila.bitila;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.longila.LongIlaFromArray;

final class BitIlaConcatenateTest {
    @Test
    void createArgumentsTest() {
        final BitIla bitIla = BitIlaFromLongIla.create(LongIlaFromArray.create(new long[1]), 0, 64);

        assertThatThrownBy(() -> BitIlaConcatenate.create(null, bitIla)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> BitIlaConcatenate.create(bitIla, null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void lengthTest() throws IOException {
        final long length = 64;
        final BitIla bitIla1 = BitIlaFromLongIla.create(LongIlaFromArray.create(new long[1]), 0, length);
        final BitIla bitIla2 = BitIlaFromLongIla.create(LongIlaFromArray.create(new long[1]), 0, length);
        final BitIla bitIla = BitIlaConcatenate.create(bitIla1, bitIla2);

        assertThat(bitIla.lengthInBits()).isEqualTo(2 * length);
    }

    @Test
    void getArgumentsTest() throws IOException {
        final BitIla bitIla1 = BitIlaFromLongIla.create(LongIlaFromArray.create(new long[1]), 0, 64);
        final BitIla bitIla2 = BitIlaFromLongIla.create(LongIlaFromArray.create(new long[1]), 0, 64);

        BitIlaCheck.checkGetArguments(BitIlaConcatenate.create(bitIla1, bitIla2));
    }
}
