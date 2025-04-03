package tfw.immutable.ila.byteila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class ByteIlaSubtractTest {
    @Test
    void argumentsTest() {
        final ByteIla ila1 = ByteIlaFromArray.create(new byte[10]);
        final ByteIla ila2 = ByteIlaFromArray.create(new byte[20]);

        assertThatThrownBy(() -> ByteIlaSubtract.create(null, ila1, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("leftIla == null not allowed!");
        assertThatThrownBy(() -> ByteIlaSubtract.create(ila1, null, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("rightIla == null not allowed!");
        assertThatThrownBy(() -> ByteIlaSubtract.create(ila1, ila2, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("leftIla.length() (=10) != rightIla.length() (=20) not allowed!");
        assertThatThrownBy(() -> ByteIlaSubtract.create(ila1, ila1, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("bufferSize (=0) < 1 not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final byte[] leftArray = new byte[length];
        final byte[] rightArray = new byte[length];
        final byte[] array = new byte[length];
        for (int ii = 0; ii < leftArray.length; ++ii) {
            leftArray[ii] = (byte) random.nextInt();
            rightArray[ii] = (byte) random.nextInt();
            array[ii] = (byte) (leftArray[ii] - rightArray[ii]);
        }
        ByteIla leftIla = ByteIlaFromArray.create(leftArray);
        ByteIla rightIla = ByteIlaFromArray.create(rightArray);
        ByteIla targetIla = ByteIlaFromArray.create(array);
        ByteIla actualIla = ByteIlaSubtract.create(leftIla, rightIla, 100);

        ByteIlaCheck.check(targetIla, actualIla);
    }
}
// AUTO GENERATED FROM TEMPLATE
