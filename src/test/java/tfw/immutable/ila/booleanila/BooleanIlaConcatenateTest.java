package tfw.immutable.ila.booleanila;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class BooleanIlaConcatenateTest {
    @Test
    void argumentsTest() {
        final BooleanIla ila = BooleanIlaFromArray.create(new boolean[10]);

        assertThatThrownBy(() -> BooleanIlaConcatenate.create(ila, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("rightIla == null not allowed!");
        assertThatThrownBy(() -> BooleanIlaConcatenate.create(null, ila))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("leftIla == null not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int leftLength = IlaTestDimensions.defaultIlaLength();
        final int rightLength = 1 + random.nextInt(leftLength);
        final boolean[] leftArray = new boolean[leftLength];
        final boolean[] rightArray = new boolean[rightLength];
        final boolean[] array = new boolean[leftLength + rightLength];
        for (int ii = 0; ii < leftArray.length; ++ii) {
            array[ii] = leftArray[ii] = random.nextBoolean();
        }
        for (int ii = 0; ii < rightArray.length; ++ii) {
            array[ii + leftLength] = rightArray[ii] = random.nextBoolean();
        }
        BooleanIla leftIla = BooleanIlaFromArray.create(leftArray);
        BooleanIla rightIla = BooleanIlaFromArray.create(rightArray);
        BooleanIla targetIla = BooleanIlaFromArray.create(array);
        BooleanIla actualIla = BooleanIlaConcatenate.create(leftIla, rightIla);

        BooleanIlaCheck.check(targetIla, actualIla);
    }

    @Test
    void closeTest() throws IOException {
        final TestCloseBooleanIla leftIla = new TestCloseBooleanIla();
        final TestCloseBooleanIla rightIla = new TestCloseBooleanIla();

        try (BooleanIla ila = BooleanIlaConcatenate.create(leftIla, rightIla)) {
            assertThat(ila).isNotNull();
        }

        assertThat(leftIla.getNumberOfCloses()).isEqualTo(1);
        assertThat(rightIla.getNumberOfCloses()).isEqualTo(1);
    }

    @Test
    void exactLeftBoundaryTest() throws Exception {
        final Random random = new Random(0);
        final boolean[] leftArray = new boolean[2];
        final boolean[] rightArray = new boolean[2];

        leftArray[0] = random.nextBoolean();
        leftArray[1] = random.nextBoolean();
        rightArray[0] = random.nextBoolean();
        rightArray[1] = random.nextBoolean();

        final BooleanIla leftIla = BooleanIlaFromArray.create(leftArray);
        final BooleanIla rightIla = BooleanIlaFromArray.create(rightArray);
        final BooleanIla actualIla = BooleanIlaConcatenate.create(leftIla, rightIla);

        try {
            final boolean[] destination = new boolean[1];

            // Read exactly the final element of the left ILA.
            actualIla.get(destination, 0, 1, 1);

            assertThat(destination[0]).isEqualTo(leftArray[1]);
        } finally {
            actualIla.close();
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
