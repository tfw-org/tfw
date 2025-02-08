package tfw.immutable.iisf.doubleiisf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import tfw.immutable.iis.doubleiis.DoubleIis;
import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.doubleila.DoubleIlaFromArray;

final class DoubleIisFactoryFromDoubleIlaTest {
    @Test
    void argumentsTest() {
        assertThatThrownBy(() -> DoubleIisFactoryFromDoubleIla.create(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ila == null not allowed!");
    }

    @Test
    void factoryTest() throws IOException {
        final double[] expectedArray = new double[11];
        final DoubleIla ila = DoubleIlaFromArray.create(expectedArray);
        final DoubleIisFactory iisf = DoubleIisFactoryFromDoubleIla.create(ila);
        final DoubleIis iis = iisf.create();
        final double[] actualArray = new double[expectedArray.length];

        assertThat(iis.read(actualArray, 0, actualArray.length)).isEqualTo(actualArray.length);
        assertThat(actualArray).isEqualTo(expectedArray);
    }
}
// AUTO GENERATED FROM TEMPLATE
