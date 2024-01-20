package tfw.immutable.ila.shortila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class ShortIlaMultiplyTest {
    @Test
    void testArguments() {
        final ShortIla ila1 = ShortIlaFromArray.create(new short[10]);
        final ShortIla ila2 = ShortIlaFromArray.create(new short[20]);

        assertThrows(IllegalArgumentException.class, () -> ShortIlaMultiply.create(null, ila1, 10));
        assertThrows(IllegalArgumentException.class, () -> ShortIlaMultiply.create(ila1, null, 10));
        assertThrows(IllegalArgumentException.class, () -> ShortIlaMultiply.create(ila1, ila1, 0));
        assertThrows(IllegalArgumentException.class, () -> ShortIlaMultiply.create(ila1, ila2, 10));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final short[] leftArray = new short[length];
        final short[] rightArray = new short[length];
        final short[] array = new short[length];
        for (int ii = 0; ii < leftArray.length; ++ii) {
            leftArray[ii] = (short) random.nextInt();
            rightArray[ii] = (short) random.nextInt();
            array[ii] = (short) (leftArray[ii] * rightArray[ii]);
        }
        ShortIla leftIla = ShortIlaFromArray.create(leftArray);
        ShortIla rightIla = ShortIlaFromArray.create(rightArray);
        ShortIla targetIla = ShortIlaFromArray.create(array);
        ShortIla actualIla = ShortIlaMultiply.create(leftIla, rightIla, 100);
        final short epsilon = (short) 0;
        ShortIlaCheck.checkAll(
                targetIla,
                actualIla,
                IlaTestDimensions.defaultOffsetLength(),
                IlaTestDimensions.defaultMaxStride(),
                epsilon);
    }
}
// AUTO GENERATED FROM TEMPLATE
