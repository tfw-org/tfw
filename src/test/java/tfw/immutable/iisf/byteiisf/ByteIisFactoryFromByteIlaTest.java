package tfw.immutable.iisf.byteiisf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import tfw.immutable.iis.byteiis.ByteIis;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaFromArray;

final class ByteIisFactoryFromByteIlaTest {
    @Test
    void argumentsTest() {
        assertThatThrownBy(() -> ByteIisFactoryFromByteIla.create(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ila == null not allowed!");
    }

    @Test
    void factoryTest() throws IOException {
        final byte[] expectedArray = new byte[11];
        final ByteIla ila = ByteIlaFromArray.create(expectedArray);
        final ByteIisFactory iisf = ByteIisFactoryFromByteIla.create(ila);
        final ByteIis iis = iisf.create();
        final byte[] actualArray = new byte[expectedArray.length];

        assertThat(iis.read(actualArray, 0, actualArray.length)).isEqualTo(actualArray.length);
        assertThat(actualArray).isEqualTo(expectedArray);
    }
}
// AUTO GENERATED FROM TEMPLATE
