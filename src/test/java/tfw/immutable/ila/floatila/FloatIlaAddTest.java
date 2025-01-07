package tfw.immutable.ila.floatila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class FloatIlaAddTest {
    @Test
    void argumentsTest() {
        final FloatIla ila1 = FloatIlaFromArray.create(new float[10]);
        final FloatIla ila2 = FloatIlaFromArray.create(new float[20]);

        assertThatThrownBy(() -> FloatIlaAdd.create(null, ila1, 10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("leftIla == null not allowed!");
        assertThatThrownBy(() -> FloatIlaAdd.create(ila1, null, 10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("rightIla == null not allowed!");
        assertThatThrownBy(() -> FloatIlaAdd.create(ila1, ila1, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("bufferSize (=0) < 1 not allowed!");
        assertThatThrownBy(() -> FloatIlaAdd.create(ila1, ila2, 10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("leftIla.length() (=10) != rightIla.length() (=20) not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final float[] leftArray = new float[length];
        final float[] rightArray = new float[length];
        final float[] array = new float[length];
        for (int ii = 0; ii < leftArray.length; ++ii) {
            leftArray[ii] = random.nextFloat();
            rightArray[ii] = random.nextFloat();
            array[ii] = leftArray[ii] + rightArray[ii];
        }
        FloatIla leftIla = FloatIlaFromArray.create(leftArray);
        FloatIla rightIla = FloatIlaFromArray.create(rightArray);
        FloatIla targetIla = FloatIlaFromArray.create(array);
        FloatIla actualIla = FloatIlaAdd.create(leftIla, rightIla, 100);

        FloatIlaCheck.check(targetIla, actualIla);
    }
}
// AUTO GENERATED FROM TEMPLATE
