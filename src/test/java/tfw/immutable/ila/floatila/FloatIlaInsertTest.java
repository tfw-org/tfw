package tfw.immutable.ila.floatila;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class FloatIlaInsertTest {
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
