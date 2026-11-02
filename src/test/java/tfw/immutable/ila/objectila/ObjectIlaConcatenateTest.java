package tfw.immutable.ila.objectila;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class ObjectIlaConcatenateTest {
    @Test
    void argumentsTest() {
        final ObjectIla<Object> ila = ObjectIlaFromArray.create(new Object[10]);

        assertThatThrownBy(() -> ObjectIlaConcatenate.create(ila, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("rightIla == null not allowed!");
        assertThatThrownBy(() -> ObjectIlaConcatenate.create(null, ila))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("leftIla == null not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int leftLength = IlaTestDimensions.defaultIlaLength();
        final int rightLength = 1 + random.nextInt(leftLength);
        final Object[] leftArray = new Object[leftLength];
        final Object[] rightArray = new Object[rightLength];
        final Object[] array = new Object[leftLength + rightLength];
        for (int ii = 0; ii < leftArray.length; ++ii) {
            array[ii] = leftArray[ii] = new Object();
        }
        for (int ii = 0; ii < rightArray.length; ++ii) {
            array[ii + leftLength] = rightArray[ii] = new Object();
        }
        ObjectIla<Object> leftIla = ObjectIlaFromArray.create(leftArray);
        ObjectIla<Object> rightIla = ObjectIlaFromArray.create(rightArray);
        ObjectIla<Object> targetIla = ObjectIlaFromArray.create(array);
        ObjectIla<Object> actualIla = ObjectIlaConcatenate.create(leftIla, rightIla);

        ObjectIlaCheck.check(targetIla, actualIla);
    }

    @Test
    void closeTest() throws IOException {
        final TestCloseObjectIla leftIla = new TestCloseObjectIla();
        final TestCloseObjectIla rightIla = new TestCloseObjectIla();

        try (ObjectIla ila = ObjectIlaConcatenate.create(leftIla, rightIla)) {
            assertThat(ila).isNotNull();
        }

        assertThat(leftIla.getNumberOfCloses()).isEqualTo(1);
        assertThat(rightIla.getNumberOfCloses()).isEqualTo(1);
    }

    @Test
    void exactLeftBoundaryTest() throws Exception {
        final Object[] leftArray = new Object[2];
        final Object[] rightArray = new Object[2];

        leftArray[0] = new Object();
        leftArray[1] = new Object();
        rightArray[0] = new Object();
        rightArray[1] = new Object();

        final ObjectIla<Object> leftIla = ObjectIlaFromArray.create(leftArray);
        final ObjectIla<Object> rightIla = ObjectIlaFromArray.create(rightArray);
        final ObjectIla<Object> actualIla = ObjectIlaConcatenate.create(leftIla, rightIla);

        try {
            final Object[] destination = new Object[1];

            // Read exactly the final element of the left ILA.
            actualIla.get(destination, 0, 1, 1);

            assertThat(destination[0]).isEqualTo(leftArray[1]);
        } finally {
            actualIla.close();
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
