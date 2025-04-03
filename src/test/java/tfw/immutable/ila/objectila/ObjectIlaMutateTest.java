package tfw.immutable.ila.objectila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class ObjectIlaMutateTest {
    @Test
    void argumentsTest() throws Exception {
        final ObjectIla<Object> ila = ObjectIlaFromArray.create(new Object[10]);
        final long ilaLength = ila.length();
        final Object value = new Object();

        assertThatThrownBy(() -> ObjectIlaMutate.create(null, 0, value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ila == null not allowed!");
        assertThatThrownBy(() -> ObjectIlaMutate.create(ila, -1, value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("index (=-1) < 0 not allowed!");
        assertThatThrownBy(() -> ObjectIlaMutate.create(ila, ilaLength, value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("index (=10) >= ila.length() (=10) not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final int length = IlaTestDimensions.defaultIlaLength();
        final Object[] array = new Object[length];
        final Object[] target = new Object[length];
        for (int index = 0; index < length; ++index) {
            for (int ii = 0; ii < array.length; ++ii) {
                array[ii] = target[ii] = new Object();
            }
            final Object value = new Object();
            target[index] = value;
            ObjectIla<Object> origIla = ObjectIlaFromArray.create(array);
            ObjectIla<Object> targetIla = ObjectIlaFromArray.create(target);
            ObjectIla<Object> actualIla = ObjectIlaMutate.create(origIla, index, value);

            ObjectIlaCheck.check(targetIla, actualIla);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
