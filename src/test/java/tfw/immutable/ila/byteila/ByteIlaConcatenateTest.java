package tfw.immutable.ila.byteila;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class ByteIlaConcatenateTest {
    @Test
    void argumentsTest() {
        final ByteIla ila = ByteIlaFromArray.create(new byte[10]);

        assertThatThrownBy(() -> ByteIlaConcatenate.create(ila, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("rightIla == null not allowed!");
        assertThatThrownBy(() -> ByteIlaConcatenate.create(null, ila))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("leftIla == null not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int leftLength = IlaTestDimensions.defaultIlaLength();
        final int rightLength = 1 + random.nextInt(leftLength);
        final byte[] leftArray = new byte[leftLength];
        final byte[] rightArray = new byte[rightLength];
        final byte[] array = new byte[leftLength + rightLength];
        for (int ii = 0; ii < leftArray.length; ++ii) {
            array[ii] = leftArray[ii] = (byte) random.nextInt();
        }
        for (int ii = 0; ii < rightArray.length; ++ii) {
            array[ii + leftLength] = rightArray[ii] = (byte) random.nextInt();
        }
        ByteIla leftIla = ByteIlaFromArray.create(leftArray);
        ByteIla rightIla = ByteIlaFromArray.create(rightArray);
        ByteIla targetIla = ByteIlaFromArray.create(array);
        ByteIla actualIla = ByteIlaConcatenate.create(leftIla, rightIla);

        ByteIlaCheck.check(targetIla, actualIla);
    }

    @Test
    void closeTest() throws IOException {
        final TestCloseByteIla leftIla = new TestCloseByteIla();
        final TestCloseByteIla rightIla = new TestCloseByteIla();

        try (ByteIla ila = ByteIlaConcatenate.create(leftIla, rightIla)) {
            assertThat(ila).isNotNull();
        }

        assertThat(leftIla.getNumberOfCloses()).isEqualTo(1);
        assertThat(rightIla.getNumberOfCloses()).isEqualTo(1);
    }

    @Test
    void exactLeftBoundaryTest() throws Exception {
        final Random random = new Random(0);
        final byte[] leftArray = new byte[2];
        final byte[] rightArray = new byte[2];

        leftArray[0] = (byte) random.nextInt();
        leftArray[1] = (byte) random.nextInt();
        rightArray[0] = (byte) random.nextInt();
        rightArray[1] = (byte) random.nextInt();

        final ByteIla leftIla = ByteIlaFromArray.create(leftArray);
        final ByteIla rightIla = ByteIlaFromArray.create(rightArray);
        final ByteIla actualIla = ByteIlaConcatenate.create(leftIla, rightIla);

        try {
            final byte[] destination = new byte[1];

            // Read exactly the final element of the left ILA.
            actualIla.get(destination, 0, 1, 1);

            assertThat(destination[0]).isEqualTo(leftArray[1]);
        } finally {
            actualIla.close();
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
