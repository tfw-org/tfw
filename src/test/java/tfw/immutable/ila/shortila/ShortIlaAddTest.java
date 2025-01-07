package tfw.immutable.ila.shortila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class ShortIlaAddTest {
    @Test
    void argumentsTest() {
        final ShortIla ila1 = ShortIlaFromArray.create(new short[10]);
        final ShortIla ila2 = ShortIlaFromArray.create(new short[20]);

        assertThatThrownBy(() -> ShortIlaAdd.create(null, ila1, 10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("leftIla == null not allowed!");
        assertThatThrownBy(() -> ShortIlaAdd.create(ila1, null, 10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("rightIla == null not allowed!");
        assertThatThrownBy(() -> ShortIlaAdd.create(ila1, ila1, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("bufferSize (=0) < 1 not allowed!");
        assertThatThrownBy(() -> ShortIlaAdd.create(ila1, ila2, 10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("leftIla.length() (=10) != rightIla.length() (=20) not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final short[] leftArray = new short[length];
        final short[] rightArray = new short[length];
        final short[] array = new short[length];
        for (int ii = 0; ii < leftArray.length; ++ii) {
            leftArray[ii] = (short) random.nextInt();
            rightArray[ii] = (short) random.nextInt();
            array[ii] = (short) (leftArray[ii] + rightArray[ii]);
        }
        ShortIla leftIla = ShortIlaFromArray.create(leftArray);
        ShortIla rightIla = ShortIlaFromArray.create(rightArray);
        ShortIla targetIla = ShortIlaFromArray.create(array);
        ShortIla actualIla = ShortIlaAdd.create(leftIla, rightIla, 100);

        ShortIlaCheck.check(targetIla, actualIla);
    }
}
// AUTO GENERATED FROM TEMPLATE
