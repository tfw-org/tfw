package tfw.immutable.iis.intiis;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.intila.IntIla;
import tfw.immutable.ila.intila.IntIlaFromArray;

final class IntIisFactoryFromIntIlaTest {
    @Test
    void argumentsTest() {
        assertThatThrownBy(() -> IntIisFactoryFromIntIla.create(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ila == null not allowed");
    }

    @Test
    void factoryTest() throws IOException {
        final int[] expectedArray = new int[11];
        final IntIla ila = IntIlaFromArray.create(expectedArray);
        final IntIisFactory iisf = IntIisFactoryFromIntIla.create(ila);
        final IntIis iis = iisf.create();
        final int[] actualArray = new int[expectedArray.length];

        assertThat(iis.read(actualArray, 0, actualArray.length)).isEqualTo(actualArray.length);
        assertThat(actualArray).isEqualTo(expectedArray);
    }
}
// AUTO GENERATED FROM TEMPLATE
