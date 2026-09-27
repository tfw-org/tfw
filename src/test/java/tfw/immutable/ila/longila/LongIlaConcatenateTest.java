package tfw.immutable.ila.longila;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class LongIlaConcatenateTest {
    @Test
    void argumentsTest() {
        final LongIla ila = LongIlaFromArray.create(new long[10]);

        assertThatThrownBy(() -> LongIlaConcatenate.create(ila, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("rightIla == null not allowed!");
        assertThatThrownBy(() -> LongIlaConcatenate.create(null, ila))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("leftIla == null not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int leftLength = IlaTestDimensions.defaultIlaLength();
        final int rightLength = 1 + random.nextInt(leftLength);
        final long[] leftArray = new long[leftLength];
        final long[] rightArray = new long[rightLength];
        final long[] array = new long[leftLength + rightLength];
        for (int ii = 0; ii < leftArray.length; ++ii) {
            array[ii] = leftArray[ii] = random.nextLong();
        }
        for (int ii = 0; ii < rightArray.length; ++ii) {
            array[ii + leftLength] = rightArray[ii] = random.nextLong();
        }
        LongIla leftIla = LongIlaFromArray.create(leftArray);
        LongIla rightIla = LongIlaFromArray.create(rightArray);
        LongIla targetIla = LongIlaFromArray.create(array);
        LongIla actualIla = LongIlaConcatenate.create(leftIla, rightIla);

        LongIlaCheck.check(targetIla, actualIla);
    }

    @Test
    void closeTest() throws IOException {
        final TestCloseLongIla leftIla = new TestCloseLongIla();
        final TestCloseLongIla rightIla = new TestCloseLongIla();

        try (LongIla ila = LongIlaConcatenate.create(leftIla, rightIla)) {
            assertThat(ila).isNotNull();
        }

        assertThat(leftIla.getNumberOfCloses()).isEqualTo(1);
        assertThat(rightIla.getNumberOfCloses()).isEqualTo(1);
    }

    @Test
    void exactLeftBoundaryTest() throws Exception {
        final Random random = new Random(0);
        final long[] leftArray = new long[2];
        final long[] rightArray = new long[2];

        leftArray[0] = random.nextLong();
        leftArray[1] = random.nextLong();
        rightArray[0] = random.nextLong();
        rightArray[1] = random.nextLong();

        final LongIla leftIla = LongIlaFromArray.create(leftArray);
        final LongIla rightIla = LongIlaFromArray.create(rightArray);
        final LongIla actualIla = LongIlaConcatenate.create(leftIla, rightIla);

        try {
            final long[] destination = new long[1];

            // Read exactly the final element of the left ILA.
            actualIla.get(destination, 0, 1, 1);

            assertThat(destination[0]).isEqualTo(leftArray[1]);
        } finally {
            actualIla.close();
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
