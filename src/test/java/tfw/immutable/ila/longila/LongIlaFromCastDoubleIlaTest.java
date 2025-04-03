package tfw.immutable.ila.longila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.doubleila.DoubleIlaFromArray;

final class LongIlaFromCastDoubleIlaTest {
    @Test
    void argumentsTest() {
        final DoubleIla ila = DoubleIlaFromArray.create(new double[10]);

        assertThatThrownBy(() -> LongIlaFromCastDoubleIla.create(null, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("doubleIla == null not allowed!");
        assertThatThrownBy(() -> LongIlaFromCastDoubleIla.create(ila, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("bufferSize (=0) < 1 not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final double[] array = new double[length];
        final long[] target = new long[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = random.nextDouble();
            target[ii] = (long) array[ii];
        }
        DoubleIla ila = DoubleIlaFromArray.create(array);
        LongIla targetIla = LongIlaFromArray.create(target);
        LongIla actualIla = LongIlaFromCastDoubleIla.create(ila, 100);

        LongIlaCheck.check(targetIla, actualIla);
    }
}
// AUTO GENERATED FROM TEMPLATE
