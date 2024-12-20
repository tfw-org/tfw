package tfw.immutable.iis.bitiis;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.bitila.BitIla;
import tfw.immutable.ila.bitila.BitIlaFromLongIla;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaFromArray;

final class BitIisFactoryFromBitIlaTest {
    @Test
    void argumentsTest() {
        assertThatThrownBy(() -> BitIisFactoryFromBitIla.create(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void factoryTest() throws IOException {
        final long[] expectedArray = new long[1];
        final LongIla longIla = LongIlaFromArray.create(expectedArray);
        final BitIla ila = BitIlaFromLongIla.create(longIla, 0, 12);
        final BitIisFactory iisf = BitIisFactoryFromBitIla.create(ila);
        final BitIis iis = iisf.create();
        final long[] actualArray = new long[expectedArray.length];

        assertThat(iis.read(actualArray, 0, actualArray.length)).isEqualTo(actualArray.length);
        assertThat(actualArray).isEqualTo(expectedArray);
    }
}
