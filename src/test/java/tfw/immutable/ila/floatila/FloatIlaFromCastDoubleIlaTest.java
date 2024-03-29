package tfw.immutable.ila.floatila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.doubleila.DoubleIlaFromArray;

class FloatIlaFromCastDoubleIlaTest {
    @Test
    void testArguments() {
        final DoubleIla ila = DoubleIlaFromArray.create(new double[10]);

        assertThrows(IllegalArgumentException.class, () -> FloatIlaFromCastDoubleIla.create(null, 1));
        assertThrows(IllegalArgumentException.class, () -> FloatIlaFromCastDoubleIla.create(ila, 0));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final double[] array = new double[length];
        final float[] target = new float[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = random.nextDouble();
            target[ii] = (float) array[ii];
        }
        DoubleIla ila = DoubleIlaFromArray.create(array);
        FloatIla targetIla = FloatIlaFromArray.create(target);
        FloatIla actualIla = FloatIlaFromCastDoubleIla.create(ila, 100);

        FloatIlaCheck.check(targetIla, actualIla);
    }
}
// AUTO GENERATED FROM TEMPLATE
