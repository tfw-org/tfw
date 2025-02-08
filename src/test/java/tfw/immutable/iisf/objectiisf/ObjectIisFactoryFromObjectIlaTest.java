package tfw.immutable.iisf.objectiisf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import tfw.immutable.iis.objectiis.ObjectIis;
import tfw.immutable.ila.objectila.ObjectIla;
import tfw.immutable.ila.objectila.ObjectIlaFromArray;

final class ObjectIisFactoryFromObjectIlaTest {
    @Test
    void argumentsTest() {
        assertThatThrownBy(() -> ObjectIisFactoryFromObjectIla.create(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ila == null not allowed!");
    }

    @Test
    void factoryTest() throws IOException {
        final Object[] expectedArray = new Object[11];
        final ObjectIla<Object> ila = ObjectIlaFromArray.create(expectedArray);
        final ObjectIisFactory<Object> iisf = ObjectIisFactoryFromObjectIla.create(ila);
        final ObjectIis<Object> iis = iisf.create();
        final Object[] actualArray = new Object[expectedArray.length];

        assertThat(iis.read(actualArray, 0, actualArray.length)).isEqualTo(actualArray.length);
        assertThat(actualArray).isEqualTo(expectedArray);
    }
}
// AUTO GENERATED FROM TEMPLATE
