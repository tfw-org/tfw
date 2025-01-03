package tfw.immutable.ila.bitila;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaCheck;
import tfw.immutable.ila.longila.LongIlaFromArray;

final class LongIlaFromBitIlaTest {
    @Test
    void createArgumentsTest() {
        assertThatThrownBy(() -> LongIlaFromBitIla.create(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void lengthTest() throws IOException {
        final long length = 64;
        final BitIla bitIla1 = BitIlaFromLongIla.create(LongIlaFromArray.create(new long[1]), 0, length);
        final LongIla longIla = LongIlaFromBitIla.create(bitIla1);

        assertThat(longIla.length()).isEqualTo(length / Long.SIZE);
    }

    @Test
    void getArgumentsTest() throws IOException {
        final BitIla bitIla1 = BitIlaFromLongIla.create(LongIlaFromArray.create(new long[1]), 0, 64);

        LongIlaCheck.checkGetArguments(LongIlaFromBitIla.create(bitIla1));
    }
}
