// booleanila,byteila,charila,doubleila,floatila,intila,longila,objectila,shortila
package ${PACKAGE};

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class ${NAME}IlaConcatenateTest {
    @Test
    void argumentsTest() {
        final ${NAME}Ila${TEMPLATE} ila = ${NAME}IlaFromArray.create(new ${TYPE}[10]);

        assertThatThrownBy(() -> ${NAME}IlaConcatenate.create(ila, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("rightIla == null not allowed!");
        assertThatThrownBy(() -> ${NAME}IlaConcatenate.create(null, ila))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("leftIla == null not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int leftLength = IlaTestDimensions.defaultIlaLength();
        final int rightLength = 1 + random.nextInt(leftLength);
        final ${TYPE}[] leftArray = new ${TYPE}[leftLength];
        final ${TYPE}[] rightArray = new ${TYPE}[rightLength];
        final ${TYPE}[] array = new ${TYPE}[leftLength + rightLength];
        for (int ii = 0; ii < leftArray.length; ++ii) {
            array[ii] = leftArray[ii] = ${RANDOM_VALUE};
        }
        for (int ii = 0; ii < rightArray.length; ++ii) {
            array[ii + leftLength] = rightArray[ii] = ${RANDOM_VALUE};
        }
        ${NAME}Ila${TEMPLATE} leftIla = ${NAME}IlaFromArray.create(leftArray);
        ${NAME}Ila${TEMPLATE} rightIla = ${NAME}IlaFromArray.create(rightArray);
        ${NAME}Ila${TEMPLATE} targetIla = ${NAME}IlaFromArray.create(array);
        ${NAME}Ila${TEMPLATE} actualIla = ${NAME}IlaConcatenate.create(leftIla, rightIla);

        ${NAME}IlaCheck.check(targetIla, actualIla);
    }

    @Test
    void closeTest() throws IOException {
        final TestClose${NAME}Ila leftIla = new TestClose${NAME}Ila();
        final TestClose${NAME}Ila rightIla = new TestClose${NAME}Ila();

        try (${NAME}Ila ila = ${NAME}IlaConcatenate.create(leftIla, rightIla)) {
            assertThat(ila).isNotNull();
        }

        assertThat(leftIla.getNumberOfCloses()).isEqualTo(1);
        assertThat(rightIla.getNumberOfCloses()).isEqualTo(1);
    }

    @Test
    void exactLeftBoundaryTest() throws Exception {
        ${RANDOM_INIT}final ${TYPE}[] leftArray = new ${TYPE}[2];
        final ${TYPE}[] rightArray = new ${TYPE}[2];

        leftArray[0] = ${RANDOM_VALUE};
        leftArray[1] = ${RANDOM_VALUE};
        rightArray[0] = ${RANDOM_VALUE};
        rightArray[1] = ${RANDOM_VALUE};

        final ${NAME}Ila${TEMPLATE} leftIla = ${NAME}IlaFromArray.create(leftArray);
        final ${NAME}Ila${TEMPLATE} rightIla = ${NAME}IlaFromArray.create(rightArray);
        final ${NAME}Ila${TEMPLATE} actualIla = ${NAME}IlaConcatenate.create(leftIla, rightIla);

        try {
            final ${TYPE}[] destination = new ${TYPE}[1];

            // Read exactly the final element of the left ILA.
            actualIla.get(destination, 0, 1, 1);

            assertThat(destination[0]).isEqualTo(leftArray[1]);
        } finally {
            actualIla.close();
        }
    }
}
