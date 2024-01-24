package tfw.immutable.ila.floatila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class FloatIlaInsertTest {
    @Test
    void testArguments() throws Exception {
        final Random random = new Random(0);
        final FloatIla ila = FloatIlaFromArray.create(new float[10]);
        final long ilaLength = ila.length();
        final float value = random.nextFloat();

        assertThrows(IllegalArgumentException.class, () -> FloatIlaInsert.create(null, 0, value));
        assertThrows(IllegalArgumentException.class, () -> FloatIlaInsert.create(ila, -1, value));
        assertThrows(IllegalArgumentException.class, () -> FloatIlaInsert.create(ila, ilaLength + 1, value));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final float[] array = new float[length];
        final float[] target = new float[length + 1];
        for (int index = 0; index < length; ++index) {
            final float value = random.nextFloat();
            int skipit = 0;
            for (int ii = 0; ii < array.length; ++ii) {
                if (index == ii) {
                    skipit = 1;
                    target[ii] = value;
                }
                target[ii + skipit] = array[ii] = random.nextFloat();
            }
            FloatIla origIla = FloatIlaFromArray.create(array);
            FloatIla targetIla = FloatIlaFromArray.create(target);
            FloatIla actualIla = FloatIlaInsert.create(origIla, index, value);

            FloatIlaCheck.check(targetIla, actualIla);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
