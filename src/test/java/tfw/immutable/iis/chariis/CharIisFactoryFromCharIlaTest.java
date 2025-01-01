package tfw.immutable.iis.chariis;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.charila.CharIla;
import tfw.immutable.ila.charila.CharIlaFromArray;

final class CharIisFactoryFromCharIlaTest {
    @Test
    void argumentsTest() {
        assertThatThrownBy(() -> CharIisFactoryFromCharIla.create(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ila == null not allowed");
    }

    @Test
    void factoryTest() throws IOException {
        final char[] expectedArray = new char[11];
        final CharIla ila = CharIlaFromArray.create(expectedArray);
        final CharIisFactory iisf = CharIisFactoryFromCharIla.create(ila);
        final CharIis iis = iisf.create();
        final char[] actualArray = new char[expectedArray.length];

        assertThat(iis.read(actualArray, 0, actualArray.length)).isEqualTo(actualArray.length);
        assertThat(actualArray).isEqualTo(expectedArray);
    }
}
// AUTO GENERATED FROM TEMPLATE
