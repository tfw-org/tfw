package tfw.immutable.ila.objectila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class ObjectIlaDecimateTest {
    @Test
    void argumentsTest() {
        final ObjectIla<Object> ila = ObjectIlaFromArray.create(new Object[10]);
        final Object[] buffer = new Object[10];

        assertThatThrownBy(() -> ObjectIlaDecimate.create(null, 2, buffer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ila == null not allowed!");
        assertThatThrownBy(() -> ObjectIlaDecimate.create(ila, 2, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("buffer == null not allowed!");
        assertThatThrownBy(() -> ObjectIlaDecimate.create(ila, 1, buffer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("factor (=1) < 2 not allowed!");
        assertThatThrownBy(() -> ObjectIlaDecimate.create(ila, 2, new Object[0]))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("buffer.length (=0) < 1 not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final int length = IlaTestDimensions.defaultIlaLength();
        final Object[] array = new Object[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = new Object();
        }
        ObjectIla<Object> ila = ObjectIlaFromArray.create(array);
        for (int factor = 2; factor <= length; ++factor) {
            final int targetLength = (length + factor - 1) / factor;
            final Object[] target = new Object[targetLength];
            for (int ii = 0; ii < target.length; ++ii) {
                target[ii] = array[ii * factor];
            }
            ObjectIla<Object> targetIla = ObjectIlaFromArray.create(target);
            ObjectIla<Object> actualIla = ObjectIlaDecimate.create(ila, factor, new Object[100]);

            ObjectIlaCheck.check(targetIla, actualIla);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
