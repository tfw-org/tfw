package tfw.immutable.ila.objectila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class ObjectIlaRemoveTest {
    @Test
    void argumentsTest() throws Exception {
        final ObjectIla<Object> ila = ObjectIlaFromArray.create(new Object[10]);
        final long ilaLength = ila.length();

        assertThatThrownBy(() -> ObjectIlaRemove.create(null, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ila == null not allowed!");
        assertThatThrownBy(() -> ObjectIlaRemove.create(ila, -1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("index (=-1) < 0 not allowed!");
        assertThatThrownBy(() -> ObjectIlaRemove.create(ila, ilaLength))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("index (=10) >= ila.length() (=10) not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final int length = IlaTestDimensions.defaultIlaLength();
        final Object[] array = new Object[length];
        final Object[] target = new Object[length - 1];
        for (int index = 0; index < length; ++index) {
            int targetii = 0;
            for (int ii = 0; ii < array.length; ++ii) {
                array[ii] = new Object();
                if (ii != index) {
                    target[targetii++] = array[ii];
                }
            }
            ObjectIla<Object> origIla = ObjectIlaFromArray.create(array);
            ObjectIla<Object> targetIla = ObjectIlaFromArray.create(target);
            ObjectIla<Object> actualIla = ObjectIlaRemove.create(origIla, index);

            ObjectIlaCheck.check(targetIla, actualIla);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
