package tfw.immutable.ila.longila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class LongIlaSubtractTest {
    @Test
    void argumentsTest() {
        final LongIla ila1 = LongIlaFromArray.create(new long[10]);
        final LongIla ila2 = LongIlaFromArray.create(new long[20]);

        assertThatThrownBy(() -> LongIlaSubtract.create(null, ila1, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("leftIla == null not allowed!");
        assertThatThrownBy(() -> LongIlaSubtract.create(ila1, null, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("rightIla == null not allowed!");
        assertThatThrownBy(() -> LongIlaSubtract.create(ila1, ila2, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("leftIla.length() (=10) != rightIla.length() (=20) not allowed!");
        assertThatThrownBy(() -> LongIlaSubtract.create(ila1, ila1, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("bufferSize (=0) < 1 not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final long[] leftArray = new long[length];
        final long[] rightArray = new long[length];
        final long[] array = new long[length];
        for (int ii = 0; ii < leftArray.length; ++ii) {
            leftArray[ii] = random.nextLong();
            rightArray[ii] = random.nextLong();
            array[ii] = leftArray[ii] - rightArray[ii];
        }
        LongIla leftIla = LongIlaFromArray.create(leftArray);
        LongIla rightIla = LongIlaFromArray.create(rightArray);
        LongIla targetIla = LongIlaFromArray.create(array);
        LongIla actualIla = LongIlaSubtract.create(leftIla, rightIla, 100);

        LongIlaCheck.check(targetIla, actualIla);
    }
}
// AUTO GENERATED FROM TEMPLATE
