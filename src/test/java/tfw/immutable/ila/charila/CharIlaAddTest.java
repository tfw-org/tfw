package tfw.immutable.ila.charila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class CharIlaAddTest {
    @Test
    void testArguments() {
        final CharIla ila1 = CharIlaFromArray.create(new char[10]);
        final CharIla ila2 = CharIlaFromArray.create(new char[20]);

        assertThrows(IllegalArgumentException.class, () -> CharIlaAdd.create(null, ila1, 10));
        assertThrows(IllegalArgumentException.class, () -> CharIlaAdd.create(ila1, null, 10));
        assertThrows(IllegalArgumentException.class, () -> CharIlaAdd.create(ila1, ila1, 0));
        assertThrows(IllegalArgumentException.class, () -> CharIlaAdd.create(ila1, ila2, 10));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final char[] leftArray = new char[length];
        final char[] rightArray = new char[length];
        final char[] array = new char[length];
        for (int ii = 0; ii < leftArray.length; ++ii) {
            leftArray[ii] = (char) random.nextInt();
            rightArray[ii] = (char) random.nextInt();
            array[ii] = (char) (leftArray[ii] + rightArray[ii]);
        }
        CharIla leftIla = CharIlaFromArray.create(leftArray);
        CharIla rightIla = CharIlaFromArray.create(rightArray);
        CharIla targetIla = CharIlaFromArray.create(array);
        CharIla actualIla = CharIlaAdd.create(leftIla, rightIla, 100);
        final char epsilon = (char) 0;
        CharIlaCheck.checkAll(
                targetIla,
                actualIla,
                IlaTestDimensions.defaultOffsetLength(),
                IlaTestDimensions.defaultMaxStride(),
                epsilon);
    }
}
// AUTO GENERATED FROM TEMPLATE
