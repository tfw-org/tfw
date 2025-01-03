package tfw.immutable.iis.longiis;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaFromArray;

final class LongIisFactoryFromLongIlaTest {
    @Test
    void argumentsTest() {
        assertThatThrownBy(() -> LongIisFactoryFromLongIla.create(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ila == null not allowed!");
    }

    @Test
    void factoryTest() throws IOException {
        final long[] expectedArray = new long[11];
        final LongIla ila = LongIlaFromArray.create(expectedArray);
        final LongIisFactory iisf = LongIisFactoryFromLongIla.create(ila);
        final LongIis iis = iisf.create();
        final long[] actualArray = new long[expectedArray.length];

        assertThat(iis.read(actualArray, 0, actualArray.length)).isEqualTo(actualArray.length);
        assertThat(actualArray).isEqualTo(expectedArray);
    }
}
// AUTO GENERATED FROM TEMPLATE
