package tfw.immutable.ila.floatila;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class FloatIlaConcatenateTest {
    @Test
    void argumentsTest() {
        final FloatIla ila = FloatIlaFromArray.create(new float[10]);

        assertThatThrownBy(() -> FloatIlaConcatenate.create(ila, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("rightIla == null not allowed!");
        assertThatThrownBy(() -> FloatIlaConcatenate.create(null, ila))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("leftIla == null not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int leftLength = IlaTestDimensions.defaultIlaLength();
        final int rightLength = 1 + random.nextInt(leftLength);
        final float[] leftArray = new float[leftLength];
        final float[] rightArray = new float[rightLength];
        final float[] array = new float[leftLength + rightLength];
        for (int ii = 0; ii < leftArray.length; ++ii) {
            array[ii] = leftArray[ii] = random.nextFloat();
        }
        for (int ii = 0; ii < rightArray.length; ++ii) {
            array[ii + leftLength] = rightArray[ii] = random.nextFloat();
        }
        FloatIla leftIla = FloatIlaFromArray.create(leftArray);
        FloatIla rightIla = FloatIlaFromArray.create(rightArray);
        FloatIla targetIla = FloatIlaFromArray.create(array);
        FloatIla actualIla = FloatIlaConcatenate.create(leftIla, rightIla);

        FloatIlaCheck.check(targetIla, actualIla);
    }

    @Test
    void closeTest() throws IOException {
        final TestCloseFloatIla leftIla = new TestCloseFloatIla();
        final TestCloseFloatIla rightIla = new TestCloseFloatIla();

        try (FloatIla ila = FloatIlaConcatenate.create(leftIla, rightIla)) {
            assertThat(ila).isNotNull();
        }

        assertThat(leftIla.getNumberOfCloses()).isEqualTo(1);
        assertThat(rightIla.getNumberOfCloses()).isEqualTo(1);
    }

    @Test
    void exactLeftBoundaryTest() throws Exception {
        final Random random = new Random(0);
        final float[] leftArray = new float[2];
        final float[] rightArray = new float[2];

        leftArray[0] = random.nextFloat();
        leftArray[1] = random.nextFloat();
        rightArray[0] = random.nextFloat();
        rightArray[1] = random.nextFloat();

        final FloatIla leftIla = FloatIlaFromArray.create(leftArray);
        final FloatIla rightIla = FloatIlaFromArray.create(rightArray);
        final FloatIla actualIla = FloatIlaConcatenate.create(leftIla, rightIla);

        try {
            final float[] destination = new float[1];

            // Read exactly the final element of the left ILA.
            actualIla.get(destination, 0, 1, 1);

            assertThat(destination[0]).isEqualTo(leftArray[1]);
        } finally {
            actualIla.close();
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
