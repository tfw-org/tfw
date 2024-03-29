package tfw.immutable.ila.doubleila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.floatila.FloatIla;
import tfw.immutable.ila.floatila.FloatIlaFromArray;

class DoubleIlaFromCastFloatIlaTest {
    @Test
    void testArguments() {
        final FloatIla ila = FloatIlaFromArray.create(new float[10]);

        assertThrows(IllegalArgumentException.class, () -> DoubleIlaFromCastFloatIla.create(null, 1));
        assertThrows(IllegalArgumentException.class, () -> DoubleIlaFromCastFloatIla.create(ila, 0));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final float[] array = new float[length];
        final double[] target = new double[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = random.nextFloat();
            target[ii] = (double) array[ii];
        }
        FloatIla ila = FloatIlaFromArray.create(array);
        DoubleIla targetIla = DoubleIlaFromArray.create(target);
        DoubleIla actualIla = DoubleIlaFromCastFloatIla.create(ila, 100);

        DoubleIlaCheck.check(targetIla, actualIla);
    }
}
// AUTO GENERATED FROM TEMPLATE
