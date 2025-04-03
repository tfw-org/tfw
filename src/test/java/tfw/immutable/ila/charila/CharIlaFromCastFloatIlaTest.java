package tfw.immutable.ila.charila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.floatila.FloatIla;
import tfw.immutable.ila.floatila.FloatIlaFromArray;

final class CharIlaFromCastFloatIlaTest {
    @Test
    void argumentsTest() {
        final FloatIla ila = FloatIlaFromArray.create(new float[10]);

        assertThatThrownBy(() -> CharIlaFromCastFloatIla.create(null, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("floatIla == null not allowed!");
        assertThatThrownBy(() -> CharIlaFromCastFloatIla.create(ila, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("bufferSize (=0) < 1 not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final float[] array = new float[length];
        final char[] target = new char[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = random.nextFloat();
            target[ii] = (char) array[ii];
        }
        FloatIla ila = FloatIlaFromArray.create(array);
        CharIla targetIla = CharIlaFromArray.create(target);
        CharIla actualIla = CharIlaFromCastFloatIla.create(ila, 100);

        CharIlaCheck.check(targetIla, actualIla);
    }
}
// AUTO GENERATED FROM TEMPLATE
