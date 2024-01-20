package tfw.immutable.ila.shortila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class ShortIlaMutateTest {
    @Test
    void testArguments() throws Exception {
        final Random random = new Random(0);
        final ShortIla ila = ShortIlaFromArray.create(new short[10]);
        final long ilaLength = ila.length();
        final short value = (short) random.nextInt();

        assertThrows(IllegalArgumentException.class, () -> ShortIlaMutate.create(null, 0, value));
        assertThrows(IllegalArgumentException.class, () -> ShortIlaMutate.create(ila, -1, value));
        assertThrows(IllegalArgumentException.class, () -> ShortIlaMutate.create(ila, ilaLength, value));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final short[] array = new short[length];
        final short[] target = new short[length];
        for (int index = 0; index < length; ++index) {
            for (int ii = 0; ii < array.length; ++ii) {
                array[ii] = target[ii] = (short) random.nextInt();
            }
            final short value = (short) random.nextInt();
            target[index] = value;
            ShortIla origIla = ShortIlaFromArray.create(array);
            ShortIla targetIla = ShortIlaFromArray.create(target);
            ShortIla actualIla = ShortIlaMutate.create(origIla, index, value);
            final short epsilon = (short) 0;
            ShortIlaCheck.checkAll(
                    targetIla,
                    actualIla,
                    IlaTestDimensions.defaultOffsetLength(),
                    IlaTestDimensions.defaultMaxStride(),
                    epsilon);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
