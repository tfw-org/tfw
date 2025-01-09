package tfw.immutable.ila.charila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class CharIlaMultiplyTest {
    @Test
    void argumentsTest() {
        final CharIla ila1 = CharIlaFromArray.create(new char[10]);
        final CharIla ila2 = CharIlaFromArray.create(new char[20]);

        assertThatThrownBy(() -> CharIlaMultiply.create(null, ila1, 10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("leftIla == null not allowed!");
        assertThatThrownBy(() -> CharIlaMultiply.create(ila1, null, 10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("rightIla == null not allowed!");
        assertThatThrownBy(() -> CharIlaMultiply.create(ila1, ila1, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("bufferSize (=0) < 1 not allowed!");
        assertThatThrownBy(() -> CharIlaMultiply.create(ila1, ila2, 10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("leftIla.length() (=10) != rightIla.length() (=20) not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final char[] leftArray = new char[length];
        final char[] rightArray = new char[length];
        final char[] array = new char[length];
        for (int ii = 0; ii < leftArray.length; ++ii) {
            leftArray[ii] = (char) random.nextInt();
            rightArray[ii] = (char) random.nextInt();
            array[ii] = (char) (leftArray[ii] * rightArray[ii]);
        }
        CharIla leftIla = CharIlaFromArray.create(leftArray);
        CharIla rightIla = CharIlaFromArray.create(rightArray);
        CharIla targetIla = CharIlaFromArray.create(array);
        CharIla actualIla = CharIlaMultiply.create(leftIla, rightIla, 100);

        CharIlaCheck.check(targetIla, actualIla);
    }
}
// AUTO GENERATED FROM TEMPLATE
