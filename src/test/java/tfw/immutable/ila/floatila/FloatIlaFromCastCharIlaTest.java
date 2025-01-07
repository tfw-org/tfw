package tfw.immutable.ila.floatila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.charila.CharIla;
import tfw.immutable.ila.charila.CharIlaFromArray;

final class FloatIlaFromCastCharIlaTest {
    @Test
    void argumentsTest() {
        final CharIla ila = CharIlaFromArray.create(new char[10]);

        assertThatThrownBy(() -> FloatIlaFromCastCharIla.create(null, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("charIla == null not allowed!");
        assertThatThrownBy(() -> FloatIlaFromCastCharIla.create(ila, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("bufferSize (=0) < 1 not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final char[] array = new char[length];
        final float[] target = new float[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = (char) random.nextInt();
            target[ii] = (float) array[ii];
        }
        CharIla ila = CharIlaFromArray.create(array);
        FloatIla targetIla = FloatIlaFromArray.create(target);
        FloatIla actualIla = FloatIlaFromCastCharIla.create(ila, 100);

        FloatIlaCheck.check(targetIla, actualIla);
    }
}
// AUTO GENERATED FROM TEMPLATE
