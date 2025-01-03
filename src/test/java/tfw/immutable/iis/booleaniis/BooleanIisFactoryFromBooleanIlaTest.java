package tfw.immutable.iis.booleaniis;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.booleanila.BooleanIla;
import tfw.immutable.ila.booleanila.BooleanIlaFromArray;

final class BooleanIisFactoryFromBooleanIlaTest {
    @Test
    void argumentsTest() {
        assertThatThrownBy(() -> BooleanIisFactoryFromBooleanIla.create(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ila == null not allowed!");
    }

    @Test
    void factoryTest() throws IOException {
        final boolean[] expectedArray = new boolean[11];
        final BooleanIla ila = BooleanIlaFromArray.create(expectedArray);
        final BooleanIisFactory iisf = BooleanIisFactoryFromBooleanIla.create(ila);
        final BooleanIis iis = iisf.create();
        final boolean[] actualArray = new boolean[expectedArray.length];

        assertThat(iis.read(actualArray, 0, actualArray.length)).isEqualTo(actualArray.length);
        assertThat(actualArray).isEqualTo(expectedArray);
    }
}
// AUTO GENERATED FROM TEMPLATE
