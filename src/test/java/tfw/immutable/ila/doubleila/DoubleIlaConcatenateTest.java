package tfw.immutable.ila.doubleila;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class DoubleIlaConcatenateTest {
    @Test
    void argumentsTest() {
        final DoubleIla ila = DoubleIlaFromArray.create(new double[10]);

        assertThatThrownBy(() -> DoubleIlaConcatenate.create(ila, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("rightIla == null not allowed!");
        assertThatThrownBy(() -> DoubleIlaConcatenate.create(null, ila))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("leftIla == null not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int leftLength = IlaTestDimensions.defaultIlaLength();
        final int rightLength = 1 + random.nextInt(leftLength);
        final double[] leftArray = new double[leftLength];
        final double[] rightArray = new double[rightLength];
        final double[] array = new double[leftLength + rightLength];
        for (int ii = 0; ii < leftArray.length; ++ii) {
            array[ii] = leftArray[ii] = random.nextDouble();
        }
        for (int ii = 0; ii < rightArray.length; ++ii) {
            array[ii + leftLength] = rightArray[ii] = random.nextDouble();
        }
        DoubleIla leftIla = DoubleIlaFromArray.create(leftArray);
        DoubleIla rightIla = DoubleIlaFromArray.create(rightArray);
        DoubleIla targetIla = DoubleIlaFromArray.create(array);
        DoubleIla actualIla = DoubleIlaConcatenate.create(leftIla, rightIla);

        DoubleIlaCheck.check(targetIla, actualIla);
    }

    @Test
    void closeTest() throws IOException {
        final TestCloseDoubleIla leftIla = new TestCloseDoubleIla();
        final TestCloseDoubleIla rightIla = new TestCloseDoubleIla();

        try (DoubleIla ila = DoubleIlaConcatenate.create(leftIla, rightIla)) {
            assertThat(ila).isNotNull();
        }

        assertThat(leftIla.getNumberOfCloses()).isEqualTo(1);
        assertThat(rightIla.getNumberOfCloses()).isEqualTo(1);
    }

    @Test
    void exactLeftBoundaryTest() throws Exception {
        final Random random = new Random(0);
        final double[] leftArray = new double[2];
        final double[] rightArray = new double[2];

        leftArray[0] = random.nextDouble();
        leftArray[1] = random.nextDouble();
        rightArray[0] = random.nextDouble();
        rightArray[1] = random.nextDouble();

        final DoubleIla leftIla = DoubleIlaFromArray.create(leftArray);
        final DoubleIla rightIla = DoubleIlaFromArray.create(rightArray);
        final DoubleIla actualIla = DoubleIlaConcatenate.create(leftIla, rightIla);

        try {
            final double[] destination = new double[1];

            // Read exactly the final element of the left ILA.
            actualIla.get(destination, 0, 1, 1);

            assertThat(destination[0]).isEqualTo(leftArray[1]);
        } finally {
            actualIla.close();
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
