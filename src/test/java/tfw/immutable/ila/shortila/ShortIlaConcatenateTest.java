package tfw.immutable.ila.shortila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class ShortIlaConcatenateTest {
    @Test
    void argumentsTest() {
        final ShortIla ila = ShortIlaFromArray.create(new short[10]);

        assertThatThrownBy(() -> ShortIlaConcatenate.create(ila, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("rightIla == null not allowed!");
        assertThatThrownBy(() -> ShortIlaConcatenate.create(null, ila))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("leftIla == null not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int leftLength = IlaTestDimensions.defaultIlaLength();
        final int rightLength = 1 + random.nextInt(leftLength);
        final short[] leftArray = new short[leftLength];
        final short[] rightArray = new short[rightLength];
        final short[] array = new short[leftLength + rightLength];
        for (int ii = 0; ii < leftArray.length; ++ii) {
            array[ii] = leftArray[ii] = (short) random.nextInt();
        }
        for (int ii = 0; ii < rightArray.length; ++ii) {
            array[ii + leftLength] = rightArray[ii] = (short) random.nextInt();
        }
        ShortIla leftIla = ShortIlaFromArray.create(leftArray);
        ShortIla rightIla = ShortIlaFromArray.create(rightArray);
        ShortIla targetIla = ShortIlaFromArray.create(array);
        ShortIla actualIla = ShortIlaConcatenate.create(leftIla, rightIla);

        ShortIlaCheck.check(targetIla, actualIla);
    }
}
// AUTO GENERATED FROM TEMPLATE
