package tfw.immutable.ila.objectila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class ObjectIlaReverseTest {
    @Test
    void argumentsTest() {
        final ObjectIla<Object> ila = ObjectIlaFromArray.create(new Object[10]);
        final Object[] buffer = new Object[10];

        assertThatThrownBy(() -> ObjectIlaReverse.create(null, buffer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ila == null not allowed!");
        assertThatThrownBy(() -> ObjectIlaReverse.create(ila, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("buffer == null not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final int length = IlaTestDimensions.defaultIlaLength();
        final Object[] array = new Object[length];
        final Object[] reversed = new Object[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = new Object();
        }
        for (int ii = 0; ii < reversed.length; ++ii) {
            reversed[ii] = array[length - 1 - ii];
        }
        ObjectIla<Object> origIla = ObjectIlaFromArray.create(array);
        ObjectIla<Object> targetIla = ObjectIlaFromArray.create(reversed);
        ObjectIla<Object> actualIla = ObjectIlaReverse.create(origIla, new Object[1000]);

        ObjectIlaCheck.check(targetIla, actualIla);
    }
}
// AUTO GENERATED FROM TEMPLATE
