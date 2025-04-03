package tfw.immutable.iisf.floatiisf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import tfw.immutable.iis.floatiis.FloatIis;
import tfw.immutable.ila.floatila.FloatIla;
import tfw.immutable.ila.floatila.FloatIlaFromArray;

final class FloatIisFactoryFromFloatIlaTest {
    @Test
    void argumentsTest() {
        assertThatThrownBy(() -> FloatIisFactoryFromFloatIla.create(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ila == null not allowed!");
    }

    @Test
    void factoryTest() throws IOException {
        final float[] expectedArray = new float[11];
        final FloatIla ila = FloatIlaFromArray.create(expectedArray);
        final FloatIisFactory iisf = FloatIisFactoryFromFloatIla.create(ila);
        final FloatIis iis = iisf.create();
        final float[] actualArray = new float[expectedArray.length];

        assertThat(iis.read(actualArray, 0, actualArray.length)).isEqualTo(actualArray.length);
        assertThat(actualArray).isEqualTo(expectedArray);
    }
}
// AUTO GENERATED FROM TEMPLATE
