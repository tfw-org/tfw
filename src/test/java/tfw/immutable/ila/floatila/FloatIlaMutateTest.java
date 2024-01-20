package tfw.immutable.ila.floatila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class FloatIlaMutateTest {
    @Test
    void testArguments() throws Exception {
        final Random random = new Random(0);
        final FloatIla ila = FloatIlaFromArray.create(new float[10]);
        final long ilaLength = ila.length();
        final float value = random.nextFloat();

        assertThrows(IllegalArgumentException.class, () -> FloatIlaMutate.create(null, 0, value));
        assertThrows(IllegalArgumentException.class, () -> FloatIlaMutate.create(ila, -1, value));
        assertThrows(IllegalArgumentException.class, () -> FloatIlaMutate.create(ila, ilaLength, value));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final float[] array = new float[length];
        final float[] target = new float[length];
        for (int index = 0; index < length; ++index) {
            for (int ii = 0; ii < array.length; ++ii) {
                array[ii] = target[ii] = random.nextFloat();
            }
            final float value = random.nextFloat();
            target[index] = value;
            FloatIla origIla = FloatIlaFromArray.create(array);
            FloatIla targetIla = FloatIlaFromArray.create(target);
            FloatIla actualIla = FloatIlaMutate.create(origIla, index, value);
            final float epsilon = 0.0f;
            FloatIlaCheck.checkAll(
                    targetIla,
                    actualIla,
                    IlaTestDimensions.defaultOffsetLength(),
                    IlaTestDimensions.defaultMaxStride(),
                    epsilon);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
