package tfw.immutable.ila.byteila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.doubleila.DoubleIlaFromArray;

class ByteIlaFromCastDoubleIlaTest {
    @Test
    void testArguments() {
        final DoubleIla ila = DoubleIlaFromArray.create(new double[10]);

        assertThrows(IllegalArgumentException.class, () -> ByteIlaFromCastDoubleIla.create(null, 1));
        assertThrows(IllegalArgumentException.class, () -> ByteIlaFromCastDoubleIla.create(ila, 0));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final double[] array = new double[length];
        final byte[] target = new byte[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = random.nextDouble();
            target[ii] = (byte) array[ii];
        }
        DoubleIla ila = DoubleIlaFromArray.create(array);
        ByteIla targetIla = ByteIlaFromArray.create(target);
        ByteIla actualIla = ByteIlaFromCastDoubleIla.create(ila, 100);

        ByteIlaCheck.check(targetIla, actualIla);
    }
}
// AUTO GENERATED FROM TEMPLATE
