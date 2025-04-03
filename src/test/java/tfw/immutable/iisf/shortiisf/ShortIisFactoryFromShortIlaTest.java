package tfw.immutable.iisf.shortiisf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import tfw.immutable.iis.shortiis.ShortIis;
import tfw.immutable.ila.shortila.ShortIla;
import tfw.immutable.ila.shortila.ShortIlaFromArray;

final class ShortIisFactoryFromShortIlaTest {
    @Test
    void argumentsTest() {
        assertThatThrownBy(() -> ShortIisFactoryFromShortIla.create(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ila == null not allowed!");
    }

    @Test
    void factoryTest() throws IOException {
        final short[] expectedArray = new short[11];
        final ShortIla ila = ShortIlaFromArray.create(expectedArray);
        final ShortIisFactory iisf = ShortIisFactoryFromShortIla.create(ila);
        final ShortIis iis = iisf.create();
        final short[] actualArray = new short[expectedArray.length];

        assertThat(iis.read(actualArray, 0, actualArray.length)).isEqualTo(actualArray.length);
        assertThat(actualArray).isEqualTo(expectedArray);
    }
}
// AUTO GENERATED FROM TEMPLATE
